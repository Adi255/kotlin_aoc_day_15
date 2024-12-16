package com.adam.aoc

import org.assertj.core.api.Assertions.assertThat
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

        assertThat(mapAndMoves.boxMap.toList()).containsExactlyElementsOf(testBoxMap().toList())
        assertThat(mapAndMoves.moves).isEqualTo(testMoves)
    }

    @Test
    fun testMoveRobotToWall() {
        val move = '<'
        val initialPosition = Pair(2, 2)
        val boxPusher = BoxPusher(testBoxMap(), initialPosition)

        val updatedPusher = boxPusher.moveBot(move)

        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(testBoxMap().toList())
        assertThat(updatedPusher.position).isEqualTo(initialPosition)
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
        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(expectedPosition)
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
        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(expectedPosition)
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
        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(expectedPosition)
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
        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(expectedPosition)
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
        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(expectedPosition)
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

        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(boxMap.toList())
        assertThat(updatedPusher.position).isEqualTo(initialPosition)
    }

    @Test
    fun testCalculateGpsSum() {
        var boxPusher = BoxPusher(testBoxMap(), Pair(2, 2))

        for (move in testMoves) {
            boxPusher = boxPusher.moveBot(move)
        }

        assertThat(boxPusher.calculateGpsSum(MapChars.BOX)).isEqualTo(2028)
    }

    @Test
    fun testPushBigBoxLeft() {
        val boxMap = arrayOf(
            charArrayOf('#', '.', '.', '.', '[', ']', '@', '#')
        )
        val move = '<'
        val initialPosition = Pair(6, 0)
        val boxPusher = BigBoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)
        val expectedMap = arrayOf(
            charArrayOf('#', '.', '.', '[', ']', '@', '.', '#')
        )

        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(Pair(5, 0))
    }

    @Test
    fun testPushBigBoxRight() {
        val boxMap = arrayOf(
            charArrayOf('#', '.', '.', '@', '[', ']', '.', '#')
        )
        val move = '>'
        val initialPosition = Pair(3, 0)
        val boxPusher = BigBoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)
        val expectedMap = arrayOf(
            charArrayOf('#', '.', '.', '.', '@', '[', ']', '#')
        )

        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(Pair(4, 0))
    }

    @Test
    fun testPushBigBoxesRight() {
        val boxMap = arrayOf(
            charArrayOf('#', '.', '.', '@', '[', ']', '[', ']', '.', '#')
        )
        val move = '>'
        val initialPosition = Pair(3, 0)
        val boxPusher = BigBoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)
        val expectedMap = arrayOf(
            charArrayOf('#', '.', '.', '.', '@', '[', ']', '[', ']', '#')
        )
        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(Pair(4, 0))
    }

    @Test
    fun testPushBigBoxesUp() {
        val boxMap = arrayOf(
            charArrayOf('#', '#'),
            charArrayOf('.', '.'),
            charArrayOf('[', ']'),
            charArrayOf('[', ']'),
            charArrayOf('@', '.')
        )
        val move = '^'
        val initialPosition = Pair(0, 4)
        val boxPusher = BigBoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)
        val expectedMap = arrayOf(
            charArrayOf('#', '#'),
            charArrayOf('[', ']'),
            charArrayOf('[', ']'),
            charArrayOf('@', '.'),
            charArrayOf('.', '.')
        )

        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(Pair(0, 3))
    }

    @Test
    fun testPushBigBoxesDown() {
        val boxMap = arrayOf(
            charArrayOf('.', '@'),
            charArrayOf('[', ']'),
            charArrayOf('[', ']'),
            charArrayOf('.', '.'),
            charArrayOf('#', '#')
        )
        val move = 'v'
        val initialPosition = Pair(1, 0)
        val boxPusher = BigBoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)
        val expectedMap = arrayOf(
            charArrayOf('.', '.'),
            charArrayOf('.', '@'),
            charArrayOf('[', ']'),
            charArrayOf('[', ']'),
            charArrayOf('#', '#')
        )
        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(Pair(1, 1))
    }


    @Test
    fun testPushSimultaneousBoxesDown() {
        val boxMap = arrayOf(
            charArrayOf('.', '@', '.'),
            charArrayOf('[', ']', '.'),
            charArrayOf('.', '[', ']'),
            charArrayOf('.', '.', '.'),
            charArrayOf('#', '#', '#')
        )
        val move = 'v'
        val initialPosition = Pair(1, 0)
        val boxPusher = BigBoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)
        val expectedMap = arrayOf(
            charArrayOf('.', '.', '.'),
            charArrayOf('.', '@', '.'),
            charArrayOf('[', ']', '.'),
            charArrayOf('.', '[', ']'),
            charArrayOf('#', '#', '#')
        )
        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(expectedMap.toList())
        assertThat(updatedPusher.position).isEqualTo(Pair(1, 1))
    }

    @Test
    fun testPushSimultaneousBoxesIntoWall() {
        val boxMap = arrayOf(
            charArrayOf('.', '@', '.'),
            charArrayOf('[', ']', '.'),
            charArrayOf('.', '[', ']'),
            charArrayOf('.', '.', '#'),
            charArrayOf('#', '#', '#')
        )
        val move = 'v'
        val initialPosition = Pair(1, 0)
        val boxPusher = BigBoxPusher(boxMap, initialPosition)

        val updatedPusher = boxPusher.moveBot(move)
        assertThat(updatedPusher.boxMap.toList()).containsExactlyElementsOf(boxMap.toList())
        assertThat(updatedPusher.position).isEqualTo(Pair(1, 0))
    }
}