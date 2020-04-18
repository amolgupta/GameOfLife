package com.github.gameoflife

import androidx.compose.Composable
import androidx.compose.remember
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Canvas
import androidx.ui.foundation.Text
import androidx.ui.geometry.Rect
import androidx.ui.graphics.Paint
import androidx.ui.graphics.Shape
import androidx.ui.layout.Arrangement
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.Spacer
import androidx.ui.layout.Table
import androidx.ui.layout.TableColumnWidth
import androidx.ui.layout.padding
import androidx.ui.layout.preferredWidth
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.ScaffoldState
import androidx.ui.material.Shapes
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

private val defaultSpacerSize = 16.dp
private val boxPadding = 1.dp

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
    Scaffold(
        scaffoldState = scaffoldState,
        topAppBar = {
            TopAppBar(
                title = { Text(text = "Jetnews") }
            )
        },
        bodyContent = {
            Column {
                GameGrid(
                    gridState = GridState(
                        setOf(Pair(1, 3), Pair(5, 7))
                    )
                )
                Controls()
            }
        }
    )
}

@Composable
fun GameGrid(gridState: GridState) {
    Column {
        for (row in 0..20) {
            Row {
                for (col in 0..20) {
                    Divider(
                        thickness = defaultSpacerSize,
                        color =
                        if (gridState.items.contains(Pair(row, col))) theme.primary else theme.background,
                        modifier = Modifier.padding(all = boxPadding).preferredWidth(defaultSpacerSize)
                    )

                }
            }
        }
    }
}

@Composable
fun Controls(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(all = defaultSpacerSize)) {
        Button(text = { Text(text = "Start") }, onClick = {})
        Spacer(Modifier.preferredWidth(defaultSpacerSize))
        Button(text = { Text(text = "Stop") }, onClick = {})
        Spacer(Modifier.preferredWidth(defaultSpacerSize))
        Button(text = { Text(text = "Reset") }, onClick = {})
    }
}

@Preview
@Composable
fun PreviewScreen() {
    AppContent()
}