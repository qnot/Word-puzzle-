package com.example.model

data class Puzzle(
    val id: String,
    val type: PuzzleType,
    val clue: String,
    val answer: String,
    val targetObjectId: String,
    val displayPattern: String = "",
    val missingLetter: Char = ' ',
    val missingLetterOptions: List<Char> = emptyList(),
    val scrambledLetters: List<Char> = emptyList(),
    val compoundClueParts: List<String> = emptyList(),
    val hintDetail: String = "",
    val points: Int = 100
)
