package com.adam.aoc

import com.adam.aoc.MapChars.Companion.DOWN
import com.adam.aoc.MapChars.Companion.UP
import com.adam.aoc.MapChars.Companion.LEFT
import com.adam.aoc.MapChars.Companion.RIGHT
import com.adam.aoc.MapChars.Companion.BOX
import com.adam.aoc.MapChars.Companion.ROBOT
import com.adam.aoc.MapChars.Companion.WALL
import com.adam.aoc.MapChars.Companion.SPACE


open class BoxPusher(val boxMap: Array<CharArray>, val position: Pair<Int, Int>) {

    open fun moveBot(move: Char): BoxPusher {
        val nextPosition = nextSquare(move)
        val newPositionOccupier = boxMap.charAt(nextPosition.first, nextPosition.second)

        when (newPositionOccupier) {
            SPACE -> {
                val nextMap = boxMap.copy()
                nextMap[position.second][position.first] = SPACE
                nextMap[nextPosition.second][nextPosition.first] = ROBOT
                return BoxPusher(nextMap, nextPosition)
            }

            BOX -> {
                return pushBoxes(nextPosition, move)
            }

            WALL -> {
                return this
            }

            else -> throw IllegalArgumentException("Invalid move")
        }
    }

    fun nextSquare(move: Char, fromPosition: Pair<Int, Int> = this.position): Pair<Int, Int> {
        return when (move) {
            UP -> Pair(fromPosition.first, fromPosition.second - 1)
            DOWN -> Pair(fromPosition.first, fromPosition.second + 1)
            LEFT -> Pair(fromPosition.first - 1, fromPosition.second)
            RIGHT -> Pair(fromPosition.first + 1, fromPosition.second)
            else -> throw IllegalArgumentException("Invalid move")
        }
    }

    private fun pushBoxes(nextPosition: Pair<Int, Int>, move: Char): BoxPusher {
        fun shiftBoxes(endPosition: Pair<Int, Int>): BoxPusher {
            val nextMap = boxMap.copy()
            nextMap.setCharAt(this.position, SPACE)
            nextMap.setCharAt(endPosition, BOX)
            nextMap.setCharAt(nextPosition, ROBOT)
            return BoxPusher(nextMap, nextPosition)
        }

        when (move) {
            in listOf(UP, DOWN) -> {
                val colsToSearch = colsToSearch(nextPosition, move)
                val offset = if (move == DOWN) 1 else -1
                for (colNum in colsToSearch.indices) {
                    val yCoord = nextPosition.second + offset * (1 + colNum)
                    val currentElement = boxMap.charAt(nextPosition.first, yCoord)
                    if (currentElement == SPACE) {
                        val endPos = Pair(nextPosition.first, yCoord)
                        return shiftBoxes(endPos)
                    } else if (currentElement == WALL) {
                        return this
                    }
                }
            }

            in listOf(LEFT, RIGHT) -> {
                val colsToSearch = colsToSearch(nextPosition, move)
                val offset = if (move == RIGHT) 1 else -1
                for (colNum in colsToSearch.indices) {
                    val xCoord = nextPosition.first + offset * (1 + colNum)
                    val currentElement = boxMap.charAt(xCoord, nextPosition.second)
                    if (currentElement == SPACE) {
                        val endPos = Pair(xCoord, nextPosition.second)
                        return shiftBoxes(endPos)
                    } else if (currentElement == WALL) {
                        return this
                    }
                }
            }
        }
        return this
    }

    private fun colsToSearch(nextPosition: Pair<Int, Int>, move: Char): List<Char> {
        return when (move) {
            UP -> boxMap.take(nextPosition.second).map { it[nextPosition.first] }.reversed()
            DOWN -> boxMap.drop(nextPosition.second + 1).map { it[nextPosition.first] }
            RIGHT -> boxMap[nextPosition.second].drop(nextPosition.first + 1)
            LEFT -> boxMap[nextPosition.second].take(nextPosition.first).reversed()
            else -> throw IllegalArgumentException("Invalid move")
        }
    }

    fun Array<CharArray>.charAt(x: Int, y: Int) = this[y][x]

    fun Array<CharArray>.setCharAt(pos: Pair<Int, Int>, c: Char) {
        this[pos.second][pos.first] = c
    }

    fun Array<CharArray>.copy() = map { it.clone() }.toTypedArray()

    fun calculateGpsSum(boxChar: Char): Int {
        var gpsSum = 0
        for (rowNum in boxMap.indices) {
            for (colNum in boxMap[rowNum].indices) {
                if (boxMap[rowNum][colNum] == boxChar) {
                    gpsSum += colNum + 100 * rowNum
                }
            }
        }
        return gpsSum
    }

    fun showBoxMap() {
        for (row in boxMap) {
            println(row)
        }
        println()
    }
}
