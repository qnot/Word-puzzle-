package com.example.model

enum class PuzzleType(val displayName: String, val instruction: String) {
    FIND_OBJECT("Find the Object", "Tap the hidden object in the scene"),
    MISSING_LETTERS("Missing Letters", "Select the missing letter to complete the word"),
    ANAGRAM("Anagram Puzzle", "Rearrange the scrambled letters into the correct word"),
    WORD_COMBINATION("Word Combination", "Combine the two clues to discover the word"),
    SCRAMBLED_LETTERS("Scrambled Letters", "Tap the letter tiles in the correct sequence"),
    DESCRIPTION_FIND("Discovery Clue", "Read the riddle and find the item in the room")
}
