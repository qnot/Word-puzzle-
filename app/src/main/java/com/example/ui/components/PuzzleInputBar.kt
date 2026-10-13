package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Puzzle
import com.example.model.PuzzleType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PuzzleInputBar(
    puzzle: Puzzle,
    revealedLettersCount: Int,
    onSolveSuccess: (String) -> Unit,
    onWrongInput: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Current spelled letters for Anagram / Scrambled types
    val enteredLetters = remember(puzzle.id) { mutableStateListOf<Char>() }
    val availableTiles = remember(puzzle.id) {
        val list = if (puzzle.type == PuzzleType.ANAGRAM || puzzle.type == PuzzleType.SCRAMBLED_LETTERS) {
            puzzle.scrambledLetters.toMutableList()
        } else {
            mutableListOf()
        }
        mutableStateListOf<Pair<Int, Char>>().apply {
            list.forEachIndexed { index, char -> add(Pair(index, char)) }
        }
    }
    val usedTileIndices = remember(puzzle.id) { mutableStateListOf<Int>() }

    // Check if auto-solved on full spelling
    LaunchedEffect(enteredLetters.size) {
        if (enteredLetters.size == puzzle.answer.length && puzzle.answer.isNotEmpty()) {
            val candidate = enteredLetters.joinToString("")
            if (candidate.equals(puzzle.answer, ignoreCase = true)) {
                onSolveSuccess(candidate)
            } else {
                onWrongInput()
            }
        }
    }

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("puzzle_input_bar")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = when (puzzle.type) {
                        PuzzleType.FIND_OBJECT -> Color(0xFF6366F1).copy(alpha = 0.15f)
                        PuzzleType.MISSING_LETTERS -> Color(0xFFEC4899).copy(alpha = 0.15f)
                        PuzzleType.ANAGRAM -> Color(0xFFF59E0B).copy(alpha = 0.15f)
                        PuzzleType.WORD_COMBINATION -> Color(0xFF10B981).copy(alpha = 0.15f)
                        PuzzleType.SCRAMBLED_LETTERS -> Color(0xFF8B5CF6).copy(alpha = 0.15f)
                        PuzzleType.DESCRIPTION_FIND -> Color(0xFF0284C7).copy(alpha = 0.15f)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = when (puzzle.type) {
                                PuzzleType.FIND_OBJECT, PuzzleType.DESCRIPTION_FIND -> Icons.Default.Search
                                PuzzleType.WORD_COMBINATION -> Icons.Default.Add
                                else -> Icons.Default.Lightbulb
                            },
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = puzzle.type.displayName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                Text(
                    text = "${puzzle.points} pts",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xFFD97706),
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Clue Text
            Text(
                text = "“${puzzle.clue}”",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    lineHeight = 22.sp
                ),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("clue_text")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Interactive mechanic depending on type
            when (puzzle.type) {
                PuzzleType.FIND_OBJECT, PuzzleType.DESCRIPTION_FIND -> {
                    // Answer slot placeholders (showing revealed letters if hints were used)
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        puzzle.answer.forEachIndexed { index, char ->
                            val isRevealed = index < revealedLettersCount
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isRevealed) Color(0xFFFEF3C7) else MaterialTheme.colorScheme.surfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(
                                    width = 1.5.dp,
                                    color = if (isRevealed) Color(0xFFF59E0B) else Color.LightGray
                                ),
                                modifier = Modifier
                                    .size(38.dp)
                                    .padding(horizontal = 2.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = if (isRevealed) char.toString() else "_",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = if (isRevealed) Color(0xFFB45309) else Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    Text(
                        text = "🔎 Tap the item in the scene above!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.outline
                        ),
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                PuzzleType.MISSING_LETTERS -> {
                    // Display word pattern e.g. C L _ C K
                    Text(
                        text = puzzle.displayPattern,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 4.sp,
                            color = MaterialTheme.colorScheme.primary
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Choose the missing letter:",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        puzzle.missingLetterOptions.forEach { letter ->
                            Surface(
                                onClick = {
                                    if (letter.equals(puzzle.missingLetter, ignoreCase = true)) {
                                        onSolveSuccess(puzzle.answer)
                                    } else {
                                        onWrongInput()
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFF4F46E5),
                                shadowElevation = 4.dp,
                                modifier = Modifier
                                    .size(48.dp)
                                    .testTag("option_letter_$letter")
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = letter.toString(),
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                PuzzleType.ANAGRAM, PuzzleType.SCRAMBLED_LETTERS -> {
                    // Target spelling slots
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        for (i in puzzle.answer.indices) {
                            val char = enteredLetters.getOrNull(i)
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (char != null) Color(0xFFFEF3C7) else MaterialTheme.colorScheme.surfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.5.dp,
                                    if (char != null) Color(0xFFF59E0B) else Color.LightGray
                                ),
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(horizontal = 2.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = char?.toString() ?: "",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color(0xFFB45309)
                                    )
                                }
                            }
                        }

                        if (enteredLetters.isNotEmpty()) {
                            IconButton(
                                onClick = {
                                    if (enteredLetters.isNotEmpty()) {
                                        enteredLetters.removeAt(enteredLetters.size - 1)
                                        if (usedTileIndices.isNotEmpty()) {
                                            usedTileIndices.removeAt(usedTileIndices.size - 1)
                                        }
                                    }
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Undo letter",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Letter tile rack
                    FlowRow(
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        availableTiles.forEach { (index, char) ->
                            val isUsed = usedTileIndices.contains(index)
                            Surface(
                                onClick = {
                                    if (!isUsed) {
                                        enteredLetters.add(char)
                                        usedTileIndices.add(index)
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isUsed) Color(0xFFE2E8F0) else Color(0xFF4F46E5),
                                shadowElevation = if (isUsed) 0.dp else 4.dp,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(46.dp)
                                    .testTag("tile_${index}_$char")
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = char.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = if (isUsed) Color(0xFF94A3B8) else Color.White
                                    )
                                }
                            }
                        }
                    }
                }

                PuzzleType.WORD_COMBINATION -> {
                    // Compound words preview
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        puzzle.compoundClueParts.forEachIndexed { idx, part ->
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (idx == 0) Color(0xFFFEF3C7) else Color(0xFFDCFCE7),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (idx == 0) Color(0xFFF59E0B) else Color(0xFF10B981)
                                ),
                                modifier = Modifier.padding(horizontal = 4.dp)
                            ) {
                                Text(
                                    text = part,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Black,
                                        color = if (idx == 0) Color(0xFFB45309) else Color(0xFF047857)
                                    ),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }

                            if (idx < puzzle.compoundClueParts.size - 1) {
                                Text(
                                    text = "+",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }

                        Text(
                            text = "=",
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )

                        Button(
                            onClick = { onSolveSuccess(puzzle.answer) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF10B981)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.testTag("combine_solve_button")
                        ) {
                            Text(
                                text = puzzle.answer,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
