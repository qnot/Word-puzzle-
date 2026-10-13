package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.navigation.Screen
import com.example.ui.screens.GameplayScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HowToPlayScreen
import com.example.ui.screens.LevelCompleteScreen
import com.example.ui.screens.LevelSelectScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WordDiscoveryQuestApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun WordDiscoveryQuestApp(viewModel: GameViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Crossfade(
        targetState = uiState.currentScreen,
        animationSpec = tween(280),
        label = "screen_crossfade"
    ) { screen ->
        when (screen) {
            is Screen.Home -> {
                HomeScreen(
                    progress = uiState.userProgress,
                    onNavigate = { targetScreen -> viewModel.navigateTo(targetScreen) },
                    onPlayClicked = {
                        val targetLevel = uiState.userProgress.highestUnlockedLevel
                        viewModel.startLevel(targetLevel)
                    }
                )
            }

            is Screen.LevelSelect -> {
                LevelSelectScreen(
                    progress = uiState.userProgress,
                    onLevelSelected = { levelId -> viewModel.startLevel(levelId) },
                    onBack = { viewModel.handleBack() }
                )
            }

            is Screen.Gameplay -> {
                GameplayScreen(
                    state = uiState,
                    onObjectTapped = { obj -> viewModel.onSceneObjectTapped(obj) },
                    onPuzzleSolved = { answer -> viewModel.onPuzzleWordSolved(answer) },
                    onMissedTap = { viewModel.handleWrongTap() },
                    onUseHint = { viewModel.useHint() },
                    onBack = { viewModel.handleBack() }
                )
            }

            is Screen.LevelComplete -> {
                LevelCompleteScreen(
                    levelId = screen.levelId,
                    score = screen.score,
                    stars = screen.stars,
                    hintsUsed = screen.hintsUsed,
                    wordsFound = screen.wordsFound,
                    onNextLevel = {
                        viewModel.startLevel(screen.levelId + 1)
                    },
                    onReplay = {
                        viewModel.startLevel(screen.levelId)
                    },
                    onHome = {
                        viewModel.navigateTo(Screen.Home)
                    }
                )
            }

            is Screen.HowToPlay -> {
                HowToPlayScreen(
                    onBack = { viewModel.handleBack() }
                )
            }

            is Screen.Settings -> {
                SettingsScreen(
                    progress = uiState.userProgress,
                    onToggleSound = { viewModel.toggleSound() },
                    onToggleVibration = { viewModel.toggleVibration() },
                    onResetProgress = { viewModel.resetAllProgress() },
                    onBack = { viewModel.handleBack() }
                )
            }
        }
    }
}
