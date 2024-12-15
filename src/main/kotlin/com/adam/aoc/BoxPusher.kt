package com.adam.aoc

class BoxPusher(val boxMap: Array<CharArray>, val position: Pair<Int, Int>) {

    companion object {
        private const val UP = '^'
        private const val DOWN = 'v'
        private const val LEFT = '<'
        private const val RIGHT = '>'
        private const val WALL = '#'
        private const val ROBOT = '@'
        private const val SPACE = '.'
        private const val BOX = 'O'
    }

    private fun nextSquare(move: Char): Pair<Int, Int> {
        return when (move) {
            UP -> Pair(position.first, position.second - 1)
            DOWN -> Pair(position.first, position.second + 1)
            LEFT -> Pair(position.first - 1, position.second)
            RIGHT -> Pair(position.first + 1, position.second)
            else -> throw IllegalArgumentException("Invalid move")
        }
    }

    fun moveBot(move: Char): BoxPusher {
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

    private fun pushBoxes(nextPosition: Pair<Int, Int>, move: Char): BoxPusher {
        fun shiftBoxes(endPosition: Pair<Int, Int>): BoxPusher {
            val nextMap = boxMap.copy()
            nextMap.setCharAt(this.position, SPACE)
            nextMap.setCharAt(endPosition, BOX)
            nextMap.setCharAt(nextPosition, ROBOT)
            return BoxPusher(nextMap, nextPosition)
        }

        when (move) {
            UP -> {
                //search above cols for space
                val colsToSearch = boxMap.take(nextPosition.second).map { it[nextPosition.first] }.reversed()
                for (colNum in colsToSearch.indices) {
                    val yCoord = nextPosition.second - 1 - colNum
                    val currentElement = boxMap.charAt(nextPosition.first, yCoord)
                    if (currentElement == SPACE) {
                        val endPos = Pair(nextPosition.first, yCoord)
                        return shiftBoxes(endPos)
                    } else if(currentElement == WALL) {
                        return this
                    }
                }
            }

            DOWN -> {
                //search above cols for space
                val colsToSearch = boxMap.drop(nextPosition.second + 1).map { it[nextPosition.first] }
                for (colNum in colsToSearch.indices) {
                    val yCoord = nextPosition.second + 1 + colNum
                    val currentElement = boxMap.charAt(nextPosition.first, yCoord)
                    if (currentElement == SPACE) {
                        val endPos = Pair(nextPosition.first, yCoord)
                        return shiftBoxes(endPos)
                    } else if(currentElement == WALL) {
                        return this
                    }
                }
            }

            RIGHT -> {
                val colsToSearch = boxMap[nextPosition.second].drop(nextPosition.first + 1)
                for (colNum in colsToSearch.indices) {
                    val xCoord = nextPosition.first + 1 + colNum
                    val currentElement = boxMap.charAt(xCoord, nextPosition.second)
                    if (currentElement == SPACE) {
                        val endPos = Pair(xCoord, nextPosition.second)
                        return shiftBoxes(endPos)
                    } else if(currentElement == WALL) {
                        return this
                    }
                }
            }

            LEFT -> {
                val colsToSearch = boxMap[nextPosition.second].take(nextPosition.first).reversed()
                for (colNum in colsToSearch.indices) {
                    val xCoord = nextPosition.first - 1 - colNum
                    val currentElement = boxMap.charAt(xCoord, nextPosition.second)
                    if (currentElement == SPACE) {
                        val endPos = Pair(xCoord, nextPosition.second)
                        return shiftBoxes(endPos)
                    } else if(currentElement == WALL) {
                        return this
                    }
                }
            }
        }
        return this
    }

    private fun Array<CharArray>.charAt(x: Int, y: Int) = this[y][x]

    private fun Array<CharArray>.setCharAt(pos: Pair<Int, Int>, c: Char) {
        this[pos.second][pos.first] = c
    }

    private fun Array<CharArray>.copy() = map { it.clone() }.toTypedArray()
}