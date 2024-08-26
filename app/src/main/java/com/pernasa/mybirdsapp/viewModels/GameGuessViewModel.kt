package com.pernasa.mybirdsapp.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pernasa.mybirdsapp.models.Bird
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameGuessViewModel(
    private val dataBirdsList: List<Bird>,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val highScoreKeySharedPrefs = "high_score"
    private val _newScore = MutableStateFlow(0)
    val newScore: StateFlow<Int> get() = _newScore

    private val _highScore = MutableStateFlow(0)
    val highScore: StateFlow<Int> get() = _highScore

    private val _currentBird = MutableStateFlow<Bird?>(null)
    val currentBird: StateFlow<Bird?> get() = _currentBird

    private val _options = MutableStateFlow<List<String>>(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _highScore.value = sharedPreferences.getInt(highScoreKeySharedPrefs, 0)
            if (dataBirdsList.isNotEmpty()) {
                _currentBird.value = dataBirdsList.random()
                loadOptionsForCurrentBird()
            }
        }
    }

    fun getOptionsForCurrentBird(): StateFlow<List<String>> {
        return _options
    }

    private fun loadOptionsForCurrentBird() {
        _options.value = generateOptionsForBird(_currentBird.value ?: return)
    }

    private fun generateOptionsForBird(bird: Bird): List<String> {
        val correctOption = bird.name
        val otherOptions = dataBirdsList.filter { it.id != bird.id }.shuffled().take(2).map { it.name }
        return (otherOptions + correctOption).shuffled()
    }

    fun onOptionSelected(option: String) {
        val correctAnswer = _currentBird.value?.name ?: return
        if (option == correctAnswer) {
            _newScore.value++
            if (_newScore.value > _highScore.value) {
                _highScore.value = _newScore.value
                sharedPreferences.edit().putInt(highScoreKeySharedPrefs, _newScore.value).apply()
            }
        } else {
            _newScore.value = 0
        }
        //moveToNextBird()
    }

    fun moveToNextBird() {
        _currentBird.value = dataBirdsList.random()
        loadOptionsForCurrentBird()
    }

    fun isCorrectOption(option: String): Boolean {
        return option == currentBird.value?.name
    }
}