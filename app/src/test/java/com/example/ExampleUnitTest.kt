package com.example

import com.example.data.LevelRepository
import com.example.model.PuzzleType
import com.example.model.UserProgress
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testLevelRepository_has10Levels() {
        val levels = LevelRepository.levels
        assertEquals(10, levels.size)
        for (i in 1..10) {
            val level = LevelRepository.getLevelById(i)
            assertNotNull("Level $i should exist", level)
            assertTrue("Level $i should have at least 5 puzzles", level!!.puzzles.size >= 5)
            assertTrue("Level $i should have scene objects", level.sceneObjects.isNotEmpty())
        }
    }

    @Test
    fun testLevel1_bedroomPuzzles() {
        val l1 = LevelRepository.getLevelById(1)
        assertNotNull(l1)
        assertEquals("Bedroom", l1!!.name)

        val answers = l1.puzzles.map { it.answer }
        assertTrue("Contains BED", answers.contains("BED"))
        assertTrue("Contains LAMP", answers.contains("LAMP"))
        assertTrue("Contains BOOK", answers.contains("BOOK"))
        assertTrue("Contains CLOCK", answers.contains("CLOCK"))
        assertTrue("Contains PILLOW", answers.contains("PILLOW"))

        val clockPuzzle = l1.puzzles.find { it.answer == "CLOCK" }
        assertEquals(PuzzleType.MISSING_LETTERS, clockPuzzle?.type)

        val pillowPuzzle = l1.puzzles.find { it.answer == "PILLOW" }
        assertEquals(PuzzleType.SCRAMBLED_LETTERS, pillowPuzzle?.type)

        assertEquals("TEDDY", l1.bonusWord?.answer)
    }

    @Test
    fun testAllPuzzlesHaveMatchingSceneObjects() {
        for (level in LevelRepository.levels) {
            val objectIds = level.sceneObjects.map { it.id }.toSet()
            for (puzzle in level.puzzles) {
                assertTrue(
                    "Puzzle '${puzzle.answer}' in level '${level.name}' must target an existing scene object '${puzzle.targetObjectId}'",
                    objectIds.contains(puzzle.targetObjectId)
                )
            }
            level.bonusWord?.let { bonus ->
                assertTrue(
                    "Bonus puzzle '${bonus.answer}' in level '${level.name}' must target an existing scene object",
                    objectIds.contains(bonus.targetObjectId)
                )
            }
        }
    }

    @Test
    fun testUserProgress_defaults() {
        val progress = UserProgress()
        assertEquals(1, progress.highestUnlockedLevel)
        assertTrue(progress.isLevelUnlocked(1))
        assertEquals(150, progress.totalCoins)
        assertEquals(0, progress.totalStars)
    }
}
