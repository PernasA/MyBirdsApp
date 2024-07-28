import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object GlobalCounterBirdsObserved {
    private val _counter = MutableLiveData(0)
    val counter: LiveData<Int> get() = _counter

    fun setCounter(value: Int) {
        _counter.value = value
    }

    private fun incrementCounter() {
        _counter.value = (_counter.value ?: 0) + 1
    }

    fun modifyCounter(isObserved: Boolean) {
        if (isObserved) {
            incrementCounter()
        } else {
            _counter.value = (_counter.value ?: 0) - 1
        }
    }
}
