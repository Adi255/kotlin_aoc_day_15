package com.adam.aoc

fun main() {
    val mapAndMoves = MapAndMoves("day15_input.txt")

    val resizedMap = MapResizer.resizeMap(mapAndMoves.boxMap)
    val initialPosition = MapAndMoves.initialRobotPosition(resizedMap)

    var boxPusher = BigBoxPusher(resizedMap, initialPosition)
    for (move in mapAndMoves.moves) {
        boxPusher = boxPusher.moveBot(move)
    }

    boxPusher.showBoxMap()
    println("GPS sum for part two: ${boxPusher.calculateGpsSum(MapChars.LEFT_BOX)}")
}


