package com.github.gameoflife

fun getNextScreen(input: MutableSet<Pair<Int, Int>>): MutableSet<Pair<Int, Int>> {
    val output: MutableSet<Pair<Int, Int>> = mutableSetOf()

    for (row in 0..gridsize) {
        for (col in 0..gridsize) {
            var neighbours = 0
            if (input.contains(Pair(row - 1, col - 1))) ++neighbours
            if (input.contains(Pair(row - 1, col))) ++neighbours
            if (input.contains(Pair(row - 1, col + 1))) ++neighbours
            if (input.contains(Pair(row, col - 1))) ++neighbours
            if (input.contains(Pair(row, col + 1))) ++neighbours
            if (input.contains(Pair(row + 1, col - 1))) ++neighbours
            if (input.contains(Pair(row + 1, col))) ++neighbours
            if (input.contains(Pair(row + 1, col + 1))) ++neighbours

            if (input.contains(Pair(row, col)) && (neighbours == 2 || neighbours == 3)) {
                output.add(Pair(row, col))
            } else if (neighbours == 3) {
                output.add(Pair(row, col))
            }
        }
    }
    return output
}