package com.example.model

data class UserProgress(
    val unlockedLevels: Set<Int> = setOf(1),
    val completedLevels: Set<Int> = emptySet(),
    val levelStars: Map<Int, Int> = emptyMap(),
    val levelHighScores: Map<Int, Int> = emptyMap(),
    val totalCoins: Int = 150,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
) {
    val totalStars: Int get() = levelStars.values.sum()
    val highestUnlockedLevel: Int get() = unlockedLevels.maxOrNull() ?: 1
    fun isLevelUnlocked(levelId: Int): Boolean = unlockedLevels.contains(levelId)
    fun getStarsForLevel(levelId: Int): Int = levelStars[levelId] ?: 0
    fun getHighScoreForLevel(levelId: Int): Int = levelHighScores[levelId] ?: 0
}
