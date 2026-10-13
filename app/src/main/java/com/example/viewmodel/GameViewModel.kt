package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SoundManager
import com.example.data.GamePreferences
import com.example.data.LevelRepository
import com.example.model.Level
import com.example.model.Puzzle
import com.example.model.PuzzleType
import com.example.model.SceneObject
import com.example.model.UserProgress
import com.example.ui.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GameUiState(
    val currentScreen: Screen = Screen.Home,
    val userProgress: UserProgress = UserProgress(),
    val activeLevel: Level? = null,
    val currentPuzzleIndex: Int = 0,
    val discoveredObjectIds: Set<String> = emptySet(),
    val discoveredBonusWord: Boolean = false,
    val currentLevelScore: Int = 0,
    val hintsUsedInLevel: Int = 0,
    val freeHintsRemaining: Int = 3,
    val isHintGlowing: Boolean = false,
    val revealedLettersCount: Int = 0,
    val feedbackMessage: String? = null,
    val isWrongShake: Boolean = false
) {
    val currentPuzzle: Puzzle?
        get() = activeLevel?.puzzles?.getOrNull(currentPuzzleIndex)

    val isLevelFinished: Boolean
        get() = activeLevel != null && currentPuzzleIndex >= activeLevel.puzzles.size
}

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = GamePreferences(application)
    val soundManager = SoundManager(application)

    private val navStack = mutableListOf<Screen>(Screen.Home)

    private val _uiState = MutableStateFlow(
        GameUiState(
            userProgress = prefs.loadProgress()
        )
    )
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            prefs.progressFlow.collect { progress ->
                soundManager.isSoundEnabled = progress.soundEnabled
                soundManager.isVibrationEnabled = progress.vibrationEnabled
                _uiState.value = _uiState.value.copy(userProgress = progress)
            }
        }
    }

    fun navigateTo(screen: Screen) {
        soundManager.playTap()
        navStack.add(screen)
        _uiState.value = _uiState.value.copy(currentScreen = screen)
    }

    fun handleBack(): Boolean {
        if (navStack.size > 1) {
            soundManager.playTap()
            navStack.removeAt(navStack.size - 1)
            val previous = navStack.last()
            _uiState.value = _uiState.value.copy(currentScreen = previous)
            return true
        }
        return false
    }

    fun startLevel(levelId: Int) {
        soundManager.playTap()
        val level = LevelRepository.getLevelById(levelId) ?: return
        _uiState.value = _uiState.value.copy(
            currentScreen = Screen.Gameplay(levelId),
            activeLevel = level,
            currentPuzzleIndex = 0,
            discoveredObjectIds = emptySet(),
            discoveredBonusWord = false,
            currentLevelScore = 0,
            hintsUsedInLevel = 0,
            freeHintsRemaining = 3,
            isHintGlowing = false,
            revealedLettersCount = 0,
            feedbackMessage = null,
            isWrongShake = false
        )
        navStack.add(Screen.Gameplay(levelId))
    }

    fun onSceneObjectTapped(obj: SceneObject) {
        val state = _uiState.value
        val level = state.activeLevel ?: return
        val currentPuzzle = state.currentPuzzle

        // Check if bonus word tapped
        if (level.bonusWord != null && obj.id == level.bonusWord.targetObjectId && !state.discoveredBonusWord) {
            soundManager.playBonus()
            val newScore = state.currentLevelScore + level.bonusWord.points
            val newDiscovered = state.discoveredObjectIds + obj.id
            _uiState.value = state.copy(
                discoveredObjectIds = newDiscovered,
                discoveredBonusWord = true,
                currentLevelScore = newScore,
                feedbackMessage = "🌟 BONUS DISCOVERED! +${level.bonusWord.points} pts"
            )
            return
        }

        if (currentPuzzle == null) return

        if (obj.id == currentPuzzle.targetObjectId) {
            handlePuzzleSuccess(currentPuzzle.answer)
        } else {
            handleWrongTap()
        }
    }

    fun onPuzzleWordSolved(answer: String) {
        val state = _uiState.value
        val currentPuzzle = state.currentPuzzle ?: return
        if (answer.equals(currentPuzzle.answer, ignoreCase = true)) {
            handlePuzzleSuccess(currentPuzzle.answer)
        } else {
            handleWrongTap()
        }
    }

    private fun handlePuzzleSuccess(word: String) {
        soundManager.playCorrect()
        val state = _uiState.value
        val level = state.activeLevel ?: return
        val currentPuzzle = state.currentPuzzle ?: return

        val earnedPoints = when {
            state.revealedLettersCount > 0 || state.isHintGlowing -> (currentPuzzle.points * 0.75f).toInt()
            else -> currentPuzzle.points
        }

        val newScore = state.currentLevelScore + earnedPoints
        val newDiscovered = state.discoveredObjectIds + currentPuzzle.targetObjectId
        val nextIndex = state.currentPuzzleIndex + 1

        _uiState.value = state.copy(
            currentLevelScore = newScore,
            discoveredObjectIds = newDiscovered,
            feedbackMessage = "✨ $word Found! +$earnedPoints pts",
            isHintGlowing = false,
            revealedLettersCount = 0
        )

        viewModelScope.launch {
            delay(1200)
            if (nextIndex >= level.puzzles.size) {
                // Completed the level!
                soundManager.playLevelComplete()
                val stars = calculateStars(newScore, state.hintsUsedInLevel, level.puzzles.size)
                val coinsEarned = 20 + (stars * 10) + (if (state.discoveredBonusWord) 15 else 0)

                prefs.completeLevel(
                    levelId = level.id,
                    stars = stars,
                    score = newScore,
                    coinsEarned = coinsEarned
                )

                val completeScreen = Screen.LevelComplete(
                    levelId = level.id,
                    score = newScore,
                    stars = stars,
                    hintsUsed = state.hintsUsedInLevel,
                    wordsFound = newDiscovered.size
                )
                navStack.add(completeScreen)
                _uiState.value = _uiState.value.copy(
                    currentScreen = completeScreen,
                    currentPuzzleIndex = nextIndex,
                    feedbackMessage = null
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    currentPuzzleIndex = nextIndex,
                    feedbackMessage = null
                )
            }
        }
    }

    private fun calculateStars(score: Int, hintsUsed: Int, totalPuzzles: Int): Int {
        return when {
            hintsUsed == 0 -> 3
            hintsUsed <= 2 -> 2
            else -> 1
        }
    }

    fun handleWrongTap() {
        soundManager.playWrong()
        val state = _uiState.value
        _uiState.value = state.copy(
            feedbackMessage = "Not quite! Look around the room...",
            isWrongShake = true
        )
        viewModelScope.launch {
            delay(600)
            _uiState.value = _uiState.value.copy(isWrongShake = false)
            delay(1400)
            if (_uiState.value.feedbackMessage == "Not quite! Look around the room...") {
                _uiState.value = _uiState.value.copy(feedbackMessage = null)
            }
        }
    }

    fun useHint() {
        val state = _uiState.value
        val currentPuzzle = state.currentPuzzle ?: return

        // Free hint or spend 20 coins
        if (state.freeHintsRemaining > 0) {
            soundManager.playHint()
            val newFree = state.freeHintsRemaining - 1
            triggerHintEffect(newFree, state.hintsUsedInLevel + 1)
        } else if (state.userProgress.totalCoins >= 20) {
            val spent = prefs.spendCoins(20)
            if (spent) {
                soundManager.playHint()
                triggerHintEffect(0, state.hintsUsedInLevel + 1)
            } else {
                _uiState.value = state.copy(feedbackMessage = "Not enough coins for a hint!")
            }
        } else {
            _uiState.value = state.copy(feedbackMessage = "No hints or coins remaining!")
        }
    }

    private fun triggerHintEffect(freeRemaining: Int, totalHintsUsed: Int) {
        val state = _uiState.value
        val currentPuzzle = state.currentPuzzle ?: return

        val newRevealed = (state.revealedLettersCount + 1).coerceAtMost(currentPuzzle.answer.length)
        _uiState.value = state.copy(
            freeHintsRemaining = freeRemaining,
            hintsUsedInLevel = totalHintsUsed,
            isHintGlowing = true,
            revealedLettersCount = newRevealed,
            feedbackMessage = "💡 Hint: ${currentPuzzle.hintDetail}"
        )
    }

    fun toggleSound() {
        val current = _uiState.value.userProgress.soundEnabled
        prefs.setSoundEnabled(!current)
        soundManager.playTap()
    }

    fun toggleVibration() {
        val current = _uiState.value.userProgress.vibrationEnabled
        prefs.setVibrationEnabled(!current)
        soundManager.playTap()
    }

    fun resetAllProgress() {
        prefs.resetAllProgress()
        soundManager.playTap()
        _uiState.value = GameUiState(
            currentScreen = Screen.Home,
            userProgress = prefs.loadProgress()
        )
        navStack.clear()
        navStack.add(Screen.Home)
    }
}
