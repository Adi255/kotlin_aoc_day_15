package com.adam.aoc

fun main() {
    val mapAndMoves = MapAndMoves("day15_input.txt")
    val initialPosition = MapAndMoves.initialRobotPosition(mapAndMoves.boxMap)

    var boxPusher = BoxPusher(mapAndMoves.boxMap, initialPosition)
    for (move in mapAndMoves.moves) {
        boxPusher = boxPusher.moveBot(move)
    }

    boxPusher.showBoxMap()
    println("GPS sum for part one: ${boxPusher.calculateGpsSum(MapChars.BOX)}")
}


