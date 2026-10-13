package com.example.model

data class Level(
    val id: Int,
    val name: String,
    val subtitle: String,
    val environment: String,
    val iconEmoji: String,
    val wallColor: Long,
    val floorColor: Long,
    val accentColor: Long,
    val skyGradientStart: Long,
    val skyGradientEnd: Long,
    val description: String,
    val sceneObjects: List<SceneObject>,
    val puzzles: List<Puzzle>,
    val bonusWord: Puzzle? = null
) {
    val totalWords: Int get() = puzzles.size + (if (bonusWord != null) 1 else 0)
}
