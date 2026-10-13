package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Level
import com.example.model.Puzzle
import com.example.model.SceneObject
import com.example.ui.components.InteractiveSceneView
import com.example.ui.components.PuzzleInputBar
import com.example.viewmodel.GameUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameplayScreen(
    state: GameUiState,
    onObjectTapped: (SceneObject) -> Unit,
    onPuzzleSolved: (String) -> Unit,
    onMissedTap: () -> Unit,
    onUseHint: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val level = state.activeLevel ?: return
    val currentPuzzle = state.currentPuzzle
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = {
                Text(
                    text = "Pause Exploration?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("Do you want to return to the map? Current level progress will be saved.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        onBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("confirm_exit_button")
                ) {
                    Text("Exit to Map")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showExitDialog = false },
                    modifier = Modifier.testTag("cancel_exit_button")
                ) {
                    Text("Resume")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${level.iconEmoji} ${level.name}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Black
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Lvl ${level.id}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(level.accentColor),
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Text(
                            text = "Word ${state.currentPuzzleIndex + 1} of ${level.puzzles.size}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFFC7D2FE)
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { showExitDialog = true },
                        modifier = Modifier.testTag("gameplay_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // Score pill
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF312E81),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFBBF24)),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFBBF24),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${state.currentLevelScore}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }

                    // Hint button
                    Button(
                        onClick = onUseHint,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.freeHintsRemaining > 0) Color(0xFFF59E0B) else Color(0xFF4F46E5)
                        ),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .testTag("hint_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Hint",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (state.freeHintsRemaining > 0) {
                                    "HINT (${state.freeHintsRemaining})"
                                } else {
                                    "HINT (20🪙)"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1E1B4B),
                    titleContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF1E1B4B),
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Level Discovery Progress bar
            LinearProgressIndicator(
                progress = {
                    (state.currentPuzzleIndex.toFloat() / level.puzzles.size.toFloat()).coerceIn(0f, 1f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = Color(0xFF10B981),
                trackColor = Color(0xFF312E81)
            )

            // Feedback Message Banner
            AnimatedVisibility(
                visible = state.feedbackMessage != null,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                state.feedbackMessage?.let { msg ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = when {
                            msg.contains("BONUS") -> Color(0xFFF59E0B)
                            msg.contains("Found") -> Color(0xFF10B981)
                            msg.contains("Hint") -> Color(0xFF4F46E5)
                            else -> Color(0xFFEF4444)
                        },
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("feedback_banner")
                    ) {
                        Text(
                            text = msg,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main Interactive Scene View
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .widthIn(max = 600.dp)
                    .padding(horizontal = 16.dp)
            ) {
                InteractiveSceneView(
                    level = level,
                    discoveredObjectIds = state.discoveredObjectIds,
                    activeTargetObjectId = currentPuzzle?.targetObjectId,
                    isHintGlowing = state.isHintGlowing,
                    onObjectTapped = onObjectTapped,
                    onMissedTap = onMissedTap,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Clue and Puzzle Input Area
            if (currentPuzzle != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 600.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    PuzzleInputBar(
                        puzzle = currentPuzzle,
                        revealedLettersCount = state.revealedLettersCount,
                        onSolveSuccess = onPuzzleSolved,
                        onWrongInput = onMissedTap,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
