package com.example.footballscoreapp.presentation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.footballscoreapp.presentation.theme.defaultProgressBarBrush

@Preview
@Composable
fun MyProgressbar(
    modifier: Modifier = Modifier,
    brush: Brush = defaultProgressBarBrush,
    size: Dp = 50.dp,
    widthOfBorder: Dp = 7.dp
) {
    val transition = rememberInfiniteTransition(label = "")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 3000, easing = LinearEasing,
            ),
        ),
        label = ""
    )
    Canvas(
        modifier = modifier
            .size(size)
            .rotate(rotation)
    ) {
        drawArc(
            startAngle = 0f,
            useCenter = false,
            sweepAngle = 300f,
            brush = defaultProgressBarBrush,
            style = Stroke(widthOfBorder.value),
        )
    }

}