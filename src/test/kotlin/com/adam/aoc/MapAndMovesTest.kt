package com.adam.aoc

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MapAndMovesTest {

    private fun testBoxMap(): Array<CharArray> {
        return arrayOf(
            charArrayOf('#', '#', '#', '#', '#', '#', '#', '#'),
            charArrayOf('#', '.', '.', 'O', '.', 'O', '.', '#'),
            charArrayOf('#', '#', '@', '.', 'O', '.', '.', '#'),
            charArrayOf('#', '.', '.', '.', 'O', '.', '.', '#'),
            charArrayOf('#', '.', '#', '.', 'O', '.', '.', '#'),
            charArrayOf('#', '.', '.', '.', 'O', '.', '.', '#'),
            charArrayOf('#', '.', '.', '.', '.', '.', '.', '#'),
            charArrayOf('#', '#', '#', '#', '#', '#', '#', '#')
        )
    }

    private val testMoves = charArrayOf('<', '^', '^', '>', '>', '>', 'v', 'v', '<', 'v', '>', '>', 'v', '<', '<')

    @Test
    fun testLoadMapAndMovesFromPath() {
        val filename = "test_input.txt"

        val mapAndMoves = MapAndMoves(filename)

        Assertions.assertThat(mapAndMoves.boxMap.toList()).containsExactlyElementsOf(testBoxMap().toList())
        Assertions.assertThat(mapAndMoves.moves).isEqualTo(testMoves)
    }

    @Test
    fun testMoveRobotToWall() {
        val move = '<'
        val initialPosition = Pair(2, 2)
        val boxPusher = BoxPusher(testBoxMap(), initialPosition)

        val updatedPusher = boxPusher.moveBot(move)

        Assertions.assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(testBoxMap().toList())
        Assertions.assertThat(updatedPusher.position).isEqualTo(initialPosition)
    }

    @Test
    fun testMoveRobotToSpace() {
        val move = 'v'
        val initialPosition = Pair(2, 2)
        val boxPusher = BoxPusher(testBoxMap(), initialPosition)

        val updatedPusher = boxPusher.moveBot(move)

        val expectedMap = testBoxMap()
        expectedMap[2][2] = '.'
        expectedMap[3][2] = '@'
        val expectedPosition = Pair(2, 3)
        Assertions.assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        Assertions.assertThat(updatedPusher.position).isEqualTo(expectedPosition)
    }

    @Test
    fun testMoveRobotRightToSingleBox() {
        val boxMap = testBoxMap()
        boxMap[1][2] = '@'
        boxMap[2][2] = '.'
        val move = '>'
        val initialPosition = Pair(2, 1)
        val boxPusher = BoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)

        val expectedMap = testBoxMap()
        expectedMap[1][3] = '@'
        expectedMap[1][4] = 'O'
        expectedMap[2][2] = '.'
        val expectedPosition = Pair(3, 1)
        Assertions.assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        Assertions.assertThat(updatedPusher.position).isEqualTo(expectedPosition)
    }

    @Test
    fun testMoveRobotUpToMultipleBoxes() {
        val boxMap = testBoxMap()
        boxMap[6][4] = '@'
        boxMap[2][2] = '.'
        val move = '^'
        val initialPosition = Pair(4, 6)
        val boxPusher = BoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)

        val expectedMap = testBoxMap()
        expectedMap[1][4] = 'O'
        expectedMap[5][4] = '@'
        expectedMap[2][2] = '.'
        val expectedPosition = Pair(4, 5)
        Assertions.assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        Assertions.assertThat(updatedPusher.position).isEqualTo(expectedPosition)
    }

    @Test
    fun testMoveRobotDownToMultipleBoxes() {
        val boxMap = arrayOf(
            charArrayOf('#'),
            charArrayOf('@'),
            charArrayOf('O'),
            charArrayOf('O'),
            charArrayOf('.'),
            charArrayOf('#')
        )
        val move = 'v'
        val initialPosition = Pair(0, 1)
        val boxPusher = BoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)

        val expectedMap = arrayOf(
            charArrayOf('#'),
            charArrayOf('.'),
            charArrayOf('@'),
            charArrayOf('O'),
            charArrayOf('O'),
            charArrayOf('#')
        )
        val expectedPosition = Pair(0, 2)
        Assertions.assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        Assertions.assertThat(updatedPusher.position).isEqualTo(expectedPosition)
    }

    @Test
    fun testMoveRobotLeftToMultipleBoxes() {
        val boxMap = arrayOf(
            charArrayOf('#', '.', 'O', 'O', 'O', 'O', '@', '#')
        )
        val move = '<'
        val initialPosition = Pair(6, 0)
        val boxPusher = BoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)

        val expectedMap = arrayOf(
            charArrayOf('#', 'O', 'O', 'O', 'O', '@', '.', '#')
        )
        val expectedPosition = Pair(5, 0)
        Assertions.assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        Assertions.assertThat(updatedPusher.position).isEqualTo(expectedPosition)
    }

    @Test
    fun testMoveRobotLeftToMultipleBoxesAgainstWall() {
        val boxMap = arrayOf(
            charArrayOf('#', '.', '.', '#', 'O', 'O', '@', '#')
        )
        val move = '<'
        val initialPosition = Pair(6, 0)
        val boxPusher = BoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)

        val expectedMap = boxMap
        val expectedPosition = initialPosition
        Assertions.assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        Assertions.assertThat(updatedPusher.position).isEqualTo(expectedPosition)
    }
}