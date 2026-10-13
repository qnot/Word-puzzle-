package com.example.ui.navigation

sealed class Screen {
    object Home : Screen()
    object LevelSelect : Screen()
    data class Gameplay(val levelId: Int) : Screen()
    data class LevelComplete(
        val levelId: Int,
        val score: Int,
        val stars: Int,
        val hintsUsed: Int,
        val wordsFound: Int
    ) : Screen()
    object HowToPlay : Screen()
    object Settings : Screen()
}
