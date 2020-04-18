package com.github.gameoflife

import androidx.compose.Model

@Model
class State(var grid: MutableSet<Pair<Int, Int>> = mutableSetOf())