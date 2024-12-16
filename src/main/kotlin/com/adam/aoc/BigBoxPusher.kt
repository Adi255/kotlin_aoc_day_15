package com.adam.aoc

import com.adam.aoc.MapChars.Companion.LEFT_BOX
import com.adam.aoc.MapChars.Companion.RIGHT_BOX
import com.adam.aoc.MapChars.Companion.ROBOT
import com.adam.aoc.MapChars.Companion.SPACE
import com.adam.aoc.MapChars.Companion.WALL

class BigBoxPusher(boxMap: Array<CharArray>, position: Pair<Int, Int>) : BoxPusher(boxMap, position) {

    override fun moveBot(move: Char): BigBoxPusher {
        val nextPosition = nextSquare(move)
        val newPositionOccupier = boxMap.charAt(nextPosition.first, nextPosition.second)

        when (newPositionOccupier) {
            SPACE -> {
                val nextMap = boxMap.copy()
                nextMap[position.second][position.first] = SPACE
                nextMap[nextPosition.second][nextPosition.first] = ROBOT
                return BigBoxPusher(nextMap, nextPosition)
            }

            WALL -> {
                return this
            }

            LEFT_BOX -> {
                return pushBoxFromLeft(nextPosition, move)
            }

            RIGHT_BOX -> {
                return pushBoxFromRight(nextPosition, move)
            }

            else -> throw IllegalArgumentException("Invalid move")
        }
    }

    private fun pushBoxFromLeft(nextPosition: Pair<Int, Int>, move: Char): BigBoxPusher {
        val rightPosition = Pair(nextPosition.first + 1, nextPosition.second)
        val nextMap = boxMap.copy()
        val movedRight = attemptMove(rightPosition, nextMap, RIGHT_BOX, move)
        if (!movedRight) return this
        val movedLeft = attemptMove(nextPosition, nextMap, LEFT_BOX, move)
        if (movedLeft) {
            nextMap.setCharAt(position, SPACE)
            nextMap.setCharAt(nextPosition, ROBOT)
            return BigBoxPusher(nextMap, nextPosition)
        } else return this
    }

    private fun pushBoxFromRight(nextPosition: Pair<Int, Int>, move: Char): BigBoxPusher {
        val leftPosition = Pair(nextPosition.first - 1, nextPosition.second)
        val nextMap = boxMap.copy()
        val movedLeft = attemptMove(leftPosition, nextMap, LEFT_BOX, move)
        if (!movedLeft) return this
        val movedRight = attemptMove(nextPosition, nextMap, RIGHT_BOX, move)
        if (movedRight) {
            nextMap.setCharAt(position, SPACE)
            nextMap.setCharAt(nextPosition, ROBOT)
            return BigBoxPusher(nextMap, nextPosition)
        } else return this
    }

    private fun attemptMove(
        fromPosition: Pair<Int, Int>,
        boxMap: Array<CharArray>,
        boxChar: Char,
        move: Char
    ): Boolean {
        val nextPosition = nextSquare(move, fromPosition)
        fun updateCurrentPosition() {
            boxMap.setCharAt(fromPosition, SPACE)
            boxMap.setCharAt(nextPosition, boxChar)
        }

        val newPositionOccupier = boxMap.charAt(nextPosition.first, nextPosition.second)
        when (newPositionOccupier) {
            WALL -> return false
            SPACE -> {
                updateCurrentPosition()
                return true
            }

            LEFT_BOX -> {
                val rightBoxPosition = nextPosition.copy(first = nextPosition.first + 1)
                val movedRight = attemptMove(rightBoxPosition, boxMap, RIGHT_BOX, move)
                if (!movedRight) {
                    return false
                }
                val movedLeft = attemptMove(nextPosition, boxMap, LEFT_BOX, move)
                if (movedLeft) {
                    updateCurrentPosition()
                    return true
                } else {
                    return false
                }
            }

            RIGHT_BOX -> {
                val leftBoxPosition = nextPosition.copy(first = nextPosition.first - 1)
                val movedLeft = attemptMove(leftBoxPosition, boxMap, LEFT_BOX, move)
                if (!movedLeft) {
                    return false
                }
                val movedRight = attemptMove(nextPosition, boxMap, RIGHT_BOX, move)
                if (movedRight) {
                    updateCurrentPosition()
                    return true
                } else {
                    return false
                }
            }

            else -> throw IllegalArgumentException("Invalid move")
        }
    }

}