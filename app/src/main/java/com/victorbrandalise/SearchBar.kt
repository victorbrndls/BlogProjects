package com.victorbrandalise

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.math.abs
import kotlin.math.absoluteValue

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xD22E2C37)),
        modifier = modifier.wrapContentHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 12.dp)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    tint = Color.White,
                    contentDescription = "Menu"
                )
            }

            Text(
                text = "Search in mail",
                color = Color(0xFFBCBAC5),
                modifier = Modifier.weight(1f)
            )

            AccountSwitcher()
        }
    }
}

private val accounts = listOf(R.drawable.goat, R.drawable.horse, R.drawable.monkey)

@Composable
fun AccountSwitcher() {
    val iconSize = 36.dp
    val iconSizePx = with(LocalDensity.current) { iconSize.toPx() }

    var selectedAccountIndex by remember { mutableStateOf(0) }
    var nextAccountIndex by remember { mutableStateOf<Int?>(null) }

    var delta by remember { mutableStateOf(0f) }

    val targetAnimation = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        snapshotFlow { delta }
            .filter { nextAccountIndex == null }
            .filter { it.absoluteValue > 1f }
            .throttleFirst(300)
            .map { delta ->
                if (delta < 0) { // Scroll down (Bottom -> Top)
                    if (selectedAccountIndex < accounts.size - 1) +1 else 0
                } else { // Scroll up (Top -> Bottom)
                    if (selectedAccountIndex > 0) -1 else 0
                }
            }
            .filter { it != 0 }
            .collect { change ->
                nextAccountIndex = selectedAccountIndex + change

                targetAnimation.animateTo(
                    change.toFloat(),
                    animationSpec = tween(easing = LinearEasing, durationMillis = 200)
                )

                selectedAccountIndex = nextAccountIndex!!
                nextAccountIndex = null
                targetAnimation.snapTo(0f)
            }
    }

    val draggableState = rememberDraggableState(onDelta = { delta = it })

    Box(modifier = Modifier.size(iconSize)) {
        nextAccountIndex?.let { index ->
            Image(
                painter = painterResource(id = accounts[index]),
                contentScale = ContentScale.Crop,
                contentDescription = "Account image",
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = abs(targetAnimation.value)
                        scaleY = abs(targetAnimation.value)
                    }
                    .clip(CircleShape)
            )
        }

        Image(
            painter = painterResource(id = accounts[selectedAccountIndex]),
            contentScale = ContentScale.Crop,
            contentDescription = "Account image",
            modifier = Modifier
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Vertical,
                )
                .graphicsLayer {
                    this.translationY = targetAnimation.value * iconSizePx * -1.5f
                }
                .clip(CircleShape) // has to be after graphicsLayer
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = android.graphics.Color.WHITE.toLong()
)
@Composable
fun SearchBarPreview() {
    Box(modifier = Modifier.padding(16.dp)) {
        SearchBar()
    }
}

fun <T> Flow<T>.throttleFirst(periodMillis: Long): Flow<T> {
    require(periodMillis > 0) { "period should be positive" }
    return flow {
        var lastTime = 0L
        collect { value ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime >= periodMillis) {
                lastTime = currentTime
                emit(value)
            }
        }
    }
}