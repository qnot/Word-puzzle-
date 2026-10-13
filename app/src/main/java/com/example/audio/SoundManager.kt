package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

/**
 * Procedural synthesizer for retro and game sound effects.
 * 100% offline, zero external file dependencies, zero latency.
 */
class SoundManager(private val context: Context) {
    private val scope = CoroutineScope(Dispatchers.Default)
    var isSoundEnabled: Boolean = true
    var isVibrationEnabled: Boolean = true

    private val vibrator: Vibrator? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            vm?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    private fun vibrate(durationMs: Long, amplitude: Int = 120) {
        if (!isVibrationEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(durationMs, amplitude))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(durationMs)
            }
        } catch (_: Exception) { }
    }

    private fun playTone(frequencies: List<Float>, durationsMs: List<Int>, volumes: List<Float> = listOf(0.7f)) {
        if (!isSoundEnabled) return
        scope.launch {
            try {
                val sampleRate = 22050
                var totalSamples = 0
                for (d in durationsMs) {
                    totalSamples += (sampleRate * d / 1000)
                }
                val buffer = ShortArray(totalSamples)
                var offset = 0

                for (i in frequencies.indices) {
                    val freq = frequencies[i]
                    val duration = durationsMs.getOrElse(i) { durationsMs.last() }
                    val count = sampleRate * duration / 1000
                    val vol = volumes.getOrElse(i) { volumes.last() }

                    for (s in 0 until count) {
                        val t = s.toDouble() / sampleRate
                        // Linear decay envelope
                        val envelope = (1.0 - (s.toDouble() / count)) * vol
                        val sample = sin(2.0 * PI * freq * t) * envelope
                        buffer[offset + s] = (sample * Short.MAX_VALUE).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                    }
                    offset += count
                }

                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                audioTrack.write(buffer, 0, buffer.size)
                audioTrack.play()
                // Auto release after sound finishes
                Thread.sleep((durationsMs.sum() + 100).toLong())
                audioTrack.release()
            } catch (_: Exception) { }
        }
    }

    fun playTap() {
        vibrate(15, 60)
        playTone(listOf(600f), listOf(40), listOf(0.3f))
    }

    fun playCorrect() {
        vibrate(35, 180)
        // Happy ascending 3-note chime: G5 -> C6 -> E6
        playTone(listOf(784f, 1046f, 1318f), listOf(80, 80, 180), listOf(0.6f, 0.7f, 0.8f))
    }

    fun playWrong() {
        vibrate(60, 200)
        // Gentle downward buzz
        playTone(listOf(220f, 160f), listOf(90, 130), listOf(0.4f, 0.4f))
    }

    fun playHint() {
        vibrate(25, 100)
        // Shimmering sparkle: high harmonic glockenspiel
        playTone(listOf(1200f, 1600f, 2000f), listOf(60, 60, 120), listOf(0.5f, 0.6f, 0.6f))
    }

    fun playBonus() {
        vibrate(45, 220)
        // Triumphant star bell
        playTone(listOf(880f, 1108f, 1320f, 1760f), listOf(70, 70, 90, 220), listOf(0.6f, 0.7f, 0.8f, 0.9f))
    }

    fun playLevelComplete() {
        vibrate(80, 240)
        // Grand fanfare: C5 -> E5 -> G5 -> C6 -> chord finish
        playTone(listOf(523f, 659f, 784f, 1046f), listOf(110, 110, 110, 360), listOf(0.7f, 0.8f, 0.9f, 1.0f))
    }
}
