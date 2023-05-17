package com.victorbrandalise

import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun BottomSheet(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .background(
                    Color(38, 52, 61),
                    RoundedCornerShape(8.dp)
                )
        ) {
            item { Item(label = "Document", position = 8) }
            item { Item(label = "Camera", position = 7) }
            item { Item(label = "Gallery", position = 6) }
            item { Item(label = "Audio", position = 5) }
            item { Item(label = "Location", position = 4) }
            item { Item(label = "Payment", position = 3) }
            item { Item(label = "Contact", position = 2) }
            item { Item(label = "Poll", position = 1) }
        }
    }
}

@Composable
private fun Item(
    label: String,
    position: Int,
) {
    var scale by remember { mutableStateOf(0.9f) }
    val animation = animateFloatAsState(
        targetValue = scale,
        animationSpec = FloatSpringSpec(
            dampingRatio = 0.3f,
        )
    )

    LaunchedEffect(Unit) {
        delay(20 + position.toLong() * 20)
        scale = 1f
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .size(48.dp)
                    .graphicsLayer {
                        scaleX = animation.value
                        scaleY = animation.value
                    }
            )
        }
        Text(
            text = label,
            color = Color(0xFF888888)
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun BottomSheetPreview() {
    BottomSheet()
}
