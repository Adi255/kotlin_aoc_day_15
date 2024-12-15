package com.adam.aoc

import java.io.File

class MapAndMoves(filePath: String) {
    private val lines: List<String> = File(this::class.java.classLoader.getResource(filePath)!!.toURI()).readLines()

    val boxMap = lines.takeWhile { it.isNotEmpty() }.map { it.toCharArray() }
    val moves = lines.reversed().filter { it.isNotEmpty() }.first().toCharArray()
}