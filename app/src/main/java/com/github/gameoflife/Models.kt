package com.github.gameoflife

enum class Status {
    Running,
    Stopped
}

data class GridState(
    val items: Set<Pair<Int, Int>>
)