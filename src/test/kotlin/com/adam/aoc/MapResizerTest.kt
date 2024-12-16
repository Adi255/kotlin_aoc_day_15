package com.adam.aoc

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class MapResizerTest {

    @Test
    fun testResizeMap() {
        val testMap = arrayOf(
            charArrayOf('#', '#', '#', '#', '#', '#', '#'),
            charArrayOf('#', '.', '.', '.', '#', '.', '#'),
            charArrayOf('#', '.', '.', '.', '.', '.', '#'),
            charArrayOf('#', '.', '.', 'O', 'O', '@', '#'),
            charArrayOf('#', '.', '.', 'O', '.', '.', '#'),
            charArrayOf('#', '.', '.', '.', '.', '.', '#'),
            charArrayOf('#', '#', '#', '#', '#', '#', '#')
        )

        val resizedMap = MapResizer.resizeMap(testMap)

        val expectedMap = arrayOf(
            charArrayOf('#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'),
            charArrayOf('#', '#', '.', '.', '.', '.', '.', '.', '#', '#', '.', '.', '#', '#'),
            charArrayOf('#', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '#'),
            charArrayOf('#', '#', '.', '.', '.', '.', '[', ']', '[', ']', '@', '.', '#', '#'),
            charArrayOf('#', '#', '.', '.', '.', '.', '[', ']', '.', '.', '.', '.', '#', '#'),
            charArrayOf('#', '#', '.', '.', '.', '.', '.', '.', '.', '.', '.', '.', '#', '#'),
            charArrayOf('#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#')
        )

        Assertions.assertThat(resizedMap.toList()).containsExactlyElementsOf(expectedMap.toList())

    }
}