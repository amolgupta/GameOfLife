package com.github.gameoflife

import androidx.compose.Composable
import androidx.compose.remember
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.Spacer
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.preferredWidth
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.ScaffoldState
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

private val defaultSpacerSize = 16.dp
private val boxPadding = 1.dp
const val gridsize = 20

@Composable
fun GameApp() {
    MaterialTheme(
        colors = theme
    ) {
        AppContent()
    }
}

@Composable
fun AppContent(scaffoldState: ScaffoldState = remember { ScaffoldState() }) {
    val gridState = State()

    Scaffold(
        scaffoldState = scaffoldState,
        topAppBar = {
            TopAppBar(
                title = { Text(text = "Game of Life") }
            )
        },
        bodyContent = {
            Column(modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalGravity = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.preferredHeight(defaultSpacerSize))
                Text(text = "Select a few boxes and then click start")
                Spacer(modifier = Modifier.preferredHeight(defaultSpacerSize))
                GameGrid(
                    state = gridState
                )
                {
                    gridState.grid = mutableSetOf<Pair<Int, Int>>()
                        .apply {
                            addAll(gridState.grid)
                            if (contains(it)) {
                                remove(it)
                            } else {
                                add(it)
                            }
                        }
                }
                Controls(state = gridState)

            }
        }
    )
}

@Composable
fun GameGrid(state: State, onUpdate: (m: Pair<Int, Int>) -> Unit) {

    Column(horizontalGravity = Alignment.CenterHorizontally) {
        for (row in 0..gridsize) {
            Row {
                for (col in 0..gridsize) {
                    Clickable(
                        onClick = {
                            state.grid.plus(Pair(row, col))
                            onUpdate(Pair(row, col))
                        }) {
                        Agent(enabled = state.grid.contains(Pair(row, col)))
                    }
                }
            }
        }
    }
}

@Composable
fun Agent(enabled: Boolean) {
    Divider(
        thickness = defaultSpacerSize,
        color =
        if (enabled) theme.primary else theme.secondary,
        modifier = Modifier.padding(all = boxPadding).preferredWidth(defaultSpacerSize)
    )
}

@Composable
fun Controls(modifier: Modifier = Modifier, state: State) {
    var job: Job? = null

    Row(modifier = modifier.padding(all = defaultSpacerSize), horizontalArrangement = Arrangement.Center) {
        Button(text = { Text(text = "Start") }, onClick = {
            job?.let { it.cancel() }
            job = play(state)
        })
        Spacer(Modifier.preferredWidth(defaultSpacerSize))
        Button(text = { Text(text = "Stop") }, onClick = {
            job?.let {
                if (it.isActive) {
                    job?.cancel(CancellationException("User canceled"))
                }
            }
        })

        Spacer(Modifier.preferredWidth(defaultSpacerSize))
        Button(text = { Text(text = "Reset") }, onClick = {
            job?.let {
                if (it.isActive) {
                    job?.cancel(CancellationException("User canceled"))
                }
            }
            state.grid = mutableSetOf()
        })
    }
}

fun play(state: State): Job? {
    return MainScope().launch {
        repeat(1000) {
            delay(400)
            if (state.grid.isEmpty()) {
                cancel()
            }
            state.grid = getNextScreen(state.grid)
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    MaterialTheme(
        colors = theme
    ) {
        AppContent()
    }
}