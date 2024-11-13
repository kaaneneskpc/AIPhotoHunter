package com.example.aiphotohunter.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: Int = 28,
    radius: Float = 60f,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Float = 10f,
    animationDuration: Int = 1000
) {

    val animateNumber = remember { androidx.compose.animation.core.Animatable(0f) }

    LaunchedEffect(percentage) {
        animateNumber.animateTo(
            targetValue = percentage,
            animationSpec = androidx.compose.animation.core.tween(durationMillis = animationDuration)
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size((radius * 2).dp)
    ) {
        Canvas(modifier = Modifier.size((radius * 2).dp)) {
            drawArc(
                color = Color.LightGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(strokeWidth)
            )
        }
        Canvas(modifier = Modifier.size((radius * 2).dp)) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * animateNumber.value,
                useCenter = false,
                style = Stroke(strokeWidth)
            )
        }
        Text(
            text = "${number}%",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = fontSize.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}