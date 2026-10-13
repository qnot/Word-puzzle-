package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.UserProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GamePreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("word_discovery_quest_prefs", Context.MODE_PRIVATE)

    private val _progressFlow = MutableStateFlow(loadProgress())
    val progressFlow: StateFlow<UserProgress> = _progressFlow.asStateFlow()

    fun loadProgress(): UserProgress {
        val unlockedRaw = prefs.getString("unlocked_levels", "1") ?: "1"
        val unlocked = unlockedRaw.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet().ifEmpty { setOf(1) }

        val completedRaw = prefs.getString("completed_levels", "") ?: ""
        val completed = completedRaw.split(",").mapNotNull { it.trim().toIntOrNull() }.toSet()

        val starsMap = mutableMapOf<Int, Int>()
        val scoresMap = mutableMapOf<Int, Int>()

        for (lvl in 1..20) {
            val s = prefs.getInt("level_stars_$lvl", 0)
            if (s > 0) starsMap[lvl] = s

            val score = prefs.getInt("level_score_$lvl", 0)
            if (score > 0) scoresMap[lvl] = score
        }

        val coins = prefs.getInt("total_coins", 150)
        val sound = prefs.getBoolean("sound_enabled", true)
        val vibration = prefs.getBoolean("vibration_enabled", true)

        return UserProgress(
            unlockedLevels = unlocked,
            completedLevels = completed,
            levelStars = starsMap,
            levelHighScores = scoresMap,
            totalCoins = coins,
            soundEnabled = sound,
            vibrationEnabled = vibration
        )
    }

    fun completeLevel(levelId: Int, stars: Int, score: Int, coinsEarned: Int): UserProgress {
        val current = _progressFlow.value
        val newUnlocked = current.unlockedLevels + (levelId + 1)
        val newCompleted = current.completedLevels + levelId
        val existingStars = current.levelStars[levelId] ?: 0
        val bestStars = maxOf(existingStars, stars)
        val existingScore = current.levelHighScores[levelId] ?: 0
        val bestScore = maxOf(existingScore, score)
        val newStarsMap = current.levelStars + (levelId to bestStars)
        val newScoresMap = current.levelHighScores + (levelId to bestScore)
        val newCoins = current.totalCoins + coinsEarned

        prefs.edit().apply {
            putString("unlocked_levels", newUnlocked.joinToString(","))
            putString("completed_levels", newCompleted.joinToString(","))
            putInt("level_stars_$levelId", bestStars)
            putInt("level_score_$levelId", bestScore)
            putInt("total_coins", newCoins)
            apply()
        }

        val updated = current.copy(
            unlockedLevels = newUnlocked,
            completedLevels = newCompleted,
            levelStars = newStarsMap,
            levelHighScores = newScoresMap,
            totalCoins = newCoins
        )
        _progressFlow.value = updated
        return updated
    }

    fun spendCoins(amount: Int): Boolean {
        val current = _progressFlow.value
        if (current.totalCoins < amount) return false
        val newCoins = current.totalCoins - amount
        prefs.edit().putInt("total_coins", newCoins).apply()
        _progressFlow.value = current.copy(totalCoins = newCoins)
        return true
    }

    fun addCoins(amount: Int) {
        val current = _progressFlow.value
        val newCoins = current.totalCoins + amount
        prefs.edit().putInt("total_coins", newCoins).apply()
        _progressFlow.value = current.copy(totalCoins = newCoins)
    }

    fun setSoundEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("sound_enabled", enabled).apply()
        _progressFlow.value = _progressFlow.value.copy(soundEnabled = enabled)
    }

    fun setVibrationEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("vibration_enabled", enabled).apply()
        _progressFlow.value = _progressFlow.value.copy(vibrationEnabled = enabled)
    }

    fun resetAllProgress() {
        prefs.edit().clear().apply()
        val fresh = UserProgress()
        _progressFlow.value = fresh
    }
}
