package de.tho.cmp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TimerScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DrawGradientCircles()
    }
}

@Composable
fun DrawGradientCircles() {

    val radius = 200f
    val strokeWidth = 10f
    val canvasSize = (radius * 2 + strokeWidth).dp

    Canvas(
        modifier = Modifier.wrapContentSize()
    ) {
        val center = Offset(size.width / 2, size.height)

        drawCircle(
            color = Color.Magenta,
            radius = radius,
            center = center,
            style = Stroke(50f)
        )
    }
}
