package com.adam.aoc

import java.io.File

class MapAndMoves(filePath: String) {
    private val lines: List<String> = File(this::class.java.classLoader.getResource(filePath)!!.toURI()).readLines()

    val boxMap = lines.takeWhile { it.isNotEmpty() }.map { it.toCharArray() }.toTypedArray()

    val moves = lines.dropWhile { it.isNotEmpty() }.joinToString("").trim().toCharArray()


    companion object {
        fun initialRobotPosition(boxMap: Array<CharArray>): Pair<Int, Int> {
            for (rowNum in boxMap.indices) {
                for (colNum in boxMap.indices) {
                    if (boxMap[rowNum][colNum] == '@') {
                        return Pair(colNum, rowNum)
                    }
                }
            }
            throw IllegalArgumentException("No robot found")
        }
    }
}