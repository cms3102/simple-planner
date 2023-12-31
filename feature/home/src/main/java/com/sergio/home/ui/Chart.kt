package com.sergio.home.ui

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.challenge.model.TaskType
import com.sergio.common.theme.PastelPurple
import com.sergio.common.theme.BananaYellow

internal val chartColors = mapOf(
    TaskType.Personal to BananaYellow,
    TaskType.Team to PastelPurple,
)

@Composable
fun PieChart(
    data: Map<String, Int>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    centerCircleContent: @Composable () -> Unit = {}
) {

    val pendingBarWidth: Dp = 35.dp
    val completeBarWidth: Dp = 25.dp
    val animDuration = 1000
    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    var animationPlayed by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(0.dp) }
    var lastValue = 0f

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) size.value else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 6f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    val density = LocalDensity.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val newSize = with(density) { it.size.width.toDp() * 0.5f }
                if (size != newSize) {
                    size = newSize
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(size * 2f)
                    .rotate(animateRotation)
            ) {
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        startAngle = lastValue,
                        sweepAngle = value,
                        useCenter = false,
                        style = Stroke(
                            if (index == 0) pendingBarWidth.toPx() else completeBarWidth.toPx(),
                            cap = StrokeCap.Butt
                        )
                    )
                    lastValue += value
                }
                drawCircle(
                    color = Color.White,
                    radius = size.value * 1.3f
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                centerCircleContent()
            }
        }
    }

}