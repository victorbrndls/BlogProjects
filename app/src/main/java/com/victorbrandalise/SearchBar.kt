package com.victorbrandalise

import androidx.annotation.DrawableRes
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

data class Account(@DrawableRes val image: Int)

private val accounts = listOf(
    Account(image = R.drawable.goat),
    Account(image = R.drawable.horse),
    Account(image = R.drawable.monkey)
)

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    var selectedAccount by remember { mutableStateOf(accounts[0]) }

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

            AccountSwitcher(
                accounts = accounts,
                currentAccount = selectedAccount,
                onAccountChanged = {
                    println(it)
                    selectedAccount = it
                }
            )
        }
    }
}

@Composable
fun AccountSwitcher(
    accounts: List<Account>,
    currentAccount: Account,
    onAccountChanged: (Account) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageSize = 36.dp
    val imageSizePx = with(LocalDensity.current) { imageSize.toPx() }

    val currentAccountIndex = accounts.indexOf(currentAccount)
    var nextAccountIndex by remember { mutableStateOf<Int?>(null) }

    var delta by remember(currentAccountIndex) { mutableStateOf(0f) }
    val draggableState = rememberDraggableState(onDelta = { delta = it })

    val targetAnimation = remember { Animatable(0f) }

    LaunchedEffect(key1 = currentAccountIndex) {
        snapshotFlow { delta }
            .filter { nextAccountIndex == null }
            .filter { it.absoluteValue > 1f }
            .throttleFirst(300)
            .map { delta ->
                if (delta < 0) { // Scroll down (Bottom -> Top)
                    if (currentAccountIndex < accounts.size - 1) 1 else 0
                } else { // Scroll up (Top -> Bottom)
                    if (currentAccountIndex > 0) -1 else 0
                }
            }
            .filter { it != 0 }
            .collect { change ->
                nextAccountIndex = currentAccountIndex + change

                targetAnimation.animateTo(
                    change.toFloat(),
                    animationSpec = tween(easing = LinearEasing, durationMillis = 200)
                )

                onAccountChanged(accounts[nextAccountIndex!!])
                nextAccountIndex = null
                targetAnimation.snapTo(0f)
            }
    }

    Box(modifier = Modifier.size(imageSize)) {
        nextAccountIndex?.let { index ->
            Image(
                painter = painterResource(id = accounts[index].image),
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
            painter = painterResource(id = accounts[currentAccountIndex].image),
            contentScale = ContentScale.Crop,
            contentDescription = "Account image",
            modifier = Modifier
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Vertical,
                )
                .graphicsLayer {
                    this.translationY = targetAnimation.value * imageSizePx * -1.5f
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