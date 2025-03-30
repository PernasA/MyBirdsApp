package com.pernasa.varillasbirdsapp.utils

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import com.pernasa.varillasbirdsapp.R

class SoundsController(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    private var mediaPlayer: MediaPlayer? = null

    private val audioList = mapOf(
        1 to R.raw.a1_tataupa_comun_a,
        100 to R.raw.a100_niacurutu,
        35 to R.raw.a35_atajacaminos_chico,
        38 to R.raw.a38_urutau,
        91 to R.raw.a91_caracolero_gallaret_varillero,
        101 to R.raw.a101_cabure_chico,
        122 to R.raw.a122_gallito_de_collar,
        124 to R.raw.a124_bandurrita_chaquenia,
        145 to R.raw.a145_mosqueta_ojo_dorado,
        158 to R.raw.a158_barullero_,
        180 to R.raw.a180_juan_chiviro,
        219 to R.raw.a219_araniero_cara_negra_,
        240 to R.raw.a240_pepitero_gris,
    )

    fun toggleSound(birdId: Int) {
        stopSound()

        val soundResId = audioList[birdId] ?: return

        stopSound() // Asegura que el anterior se libere antes de iniciar uno nuevo

        mediaPlayer = MediaPlayer.create(context, soundResId).apply {
            setVolume(0.8f, 0.8f)
            start()

            setOnCompletionListener {
                stopSound() // Detiene el sonido y libera la memoria al finalizar
            }
        }
    }

    fun stopSound() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop() // Detiene la reproducción
            }
            it.release() // Libera los recursos del MediaPlayer
        }
        mediaPlayer = null // Evita referencias colgadas
    }

    override fun onCleared() {
        super.onCleared()
        stopSound() // Se ejecuta cuando se cierra la pantalla
    }
}
