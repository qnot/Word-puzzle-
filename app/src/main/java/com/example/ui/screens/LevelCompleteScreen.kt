package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LevelRepository

@Composable
fun LevelCompleteScreen(
    levelId: Int,
    score: Int,
    stars: Int,
    hintsUsed: Int,
    wordsFound: Int,
    onNextLevel: () -> Unit,
    onReplay: () -> Unit,
    onHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onHome() }

    val level = LevelRepository.getLevelById(levelId)
    val hasNextLevel = LevelRepository.getLevelById(levelId + 1) != null

    var startAnim by remember { mutableStateOf(false) }
    val starScale by animateFloatAsState(
        targetValue = if (startAnim) 1f else 0.2f,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "starScale"
    )

    LaunchedEffect(Unit) {
        startAnim = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1E1B4B), Color(0xFF312E81), Color(0xFF0F172A))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 500.dp)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Trophy / Party Emblem
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(90.dp)
                    .background(Color(0xFFF59E0B), CircleShape)
                    .border(4.dp, Color(0xFFFDE047), CircleShape)
            ) {
                Text(text = "🏆", fontSize = 46.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "LEVEL COMPLETE!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFFDE047),
                    letterSpacing = 2.sp
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = "${level?.name ?: "Level $levelId"} Cleared",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 3 Stars Display
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .scale(starScale)
                    .padding(vertical = 8.dp)
            ) {
                for (i in 1..3) {
                    val isEarned = i <= stars
                    Icon(
                        imageVector = if (isEarned) Icons.Default.Star else Icons.Outlined.Star,
                        contentDescription = "Star $i",
                        tint = if (isEarned) Color(0xFFFBBF24) else Color(0xFF475569),
                        modifier = Modifier
                            .size(if (i == 2) 58.dp else 46.dp)
                            .padding(horizontal = 4.dp)
                    )
                }
            }

            Text(
                text = when (stars) {
                    3 -> "⭐⭐⭐ EXCELLENT QUEST!"
                    2 -> "⭐⭐ GREAT JOB!"
                    else -> "⭐ COMPLETED!"
                },
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFDE047)
                ),
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Score Summary Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B4B).copy(alpha = 0.8f)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF6366F1)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    SummaryRow(label = "Score Earned", value = "+$score pts", valueColor = Color(0xFFFDE047))
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(label = "Words Discovered", value = "$wordsFound words", valueColor = Color(0xFF10B981))
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(label = "Hints Used", value = "$hintsUsed", valueColor = Color(0xFF93C5FD))
                    Spacer(modifier = Modifier.height(8.dp))
                    SummaryRow(label = "Coin Bonus", value = "+${20 + (stars * 10)} 🪙", valueColor = Color(0xFFFACC15))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Action Buttons
            if (hasNextLevel) {
                Button(
                    onClick = onNextLevel,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("next_level_button")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "NEXT LEVEL",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onReplay,
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF818CF8)),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("replay_button")
                ) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "REPLAY", fontWeight = FontWeight.Bold, color = Color.White)
                }

                OutlinedButton(
                    onClick = onHome,
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF818CF8)),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("home_button")
                ) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "HOME", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    valueColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFC7D2FE))
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
        )
    }
}
