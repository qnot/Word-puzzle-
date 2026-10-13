package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LevelRepository
import com.example.model.Level
import com.example.model.UserProgress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelSelectScreen(
    progress: UserProgress,
    onLevelSelected: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "EXPLORATION MAP",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("level_select_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF312E81).copy(alpha = 0.8f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFBBF24)),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFBBF24),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${progress.totalStars}/30",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFFDE047)
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 600.dp)
                    .testTag("level_list")
            ) {
                items(LevelRepository.levels, key = { it.id }) { level ->
                    val isUnlocked = progress.isLevelUnlocked(level.id)
                    val stars = progress.getStarsForLevel(level.id)
                    val highScore = progress.getHighScoreForLevel(level.id)

                    LevelCard(
                        level = level,
                        isUnlocked = isUnlocked,
                        stars = stars,
                        highScore = highScore,
                        onSelect = { if (isUnlocked) onLevelSelected(level.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun LevelCard(
    level: Level,
    isUnlocked: Boolean,
    stars: Int,
    highScore: Int,
    onSelect: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) Color(0xFF312E81) else Color(0xFF1F2937).copy(alpha = 0.7f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isUnlocked) 2.dp else 1.dp,
            color = if (isUnlocked) Color(level.accentColor) else Color.DarkGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isUnlocked) 6.dp else 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isUnlocked, onClick = onSelect)
            .testTag("level_card_${level.id}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Level Avatar / Lock Icon
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(62.dp)
                    .background(
                        if (isUnlocked) {
                            Brush.linearGradient(
                                listOf(Color(level.skyGradientStart), Color(level.skyGradientEnd))
                            )
                        } else {
                            Brush.linearGradient(
                                listOf(Color(0xFF374151), Color(0xFF1F2937))
                            )
                        },
                        CircleShape
                    )
                    .border(
                        2.dp,
                        if (isUnlocked) Color(level.accentColor) else Color.Gray,
                        CircleShape
                    )
            ) {
                if (isUnlocked) {
                    Text(text = level.iconEmoji, fontSize = 28.sp)
                } else {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color(0xFF9CA3AF),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Level Info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "LEVEL ${level.id}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) Color(level.accentColor) else Color.Gray,
                            letterSpacing = 1.sp
                        )
                    )
                    if (highScore > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "• $highScore pts",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFFFBBF24),
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Text(
                    text = level.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Black,
                        color = if (isUnlocked) Color.White else Color.Gray
                    )
                )

                Text(
                    text = level.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (isUnlocked) Color(0xFFC7D2FE) else Color.DarkGray
                    )
                )

                if (isUnlocked) {
                    Spacer(modifier = Modifier.height(6.dp))
                    // Stars Row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        for (i in 1..3) {
                            Icon(
                                imageVector = if (i <= stars) Icons.Default.Star else Icons.Outlined.Star,
                                contentDescription = null,
                                tint = if (i <= stars) Color(0xFFFBBF24) else Color(0xFF4B5563),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                }
            }

            // Action / Status indicator
            if (isUnlocked) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF10B981),
                    modifier = Modifier.size(42.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            } else {
                Text(
                    text = "LOCKED",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
