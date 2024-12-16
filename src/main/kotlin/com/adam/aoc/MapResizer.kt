package com.adam.aoc

class MapResizer {

    companion object {
        fun resizeMap(boxMap: Array<CharArray>): Array<CharArray> {
            return boxMap.map { row -> row.flatMap { doubleChar(it) }.toCharArray() }.toTypedArray()
        }

        private fun doubleChar(char: Char): List<Char> {
            if (char == MapChars.ROBOT) {
                return listOf(char, MapChars.SPACE)
            } else if (char == MapChars.BOX) {
                return listOf(MapChars.LEFT_BOX, MapChars.RIGHT_BOX)
            }
            return listOf(char, char)
        }
    }
}