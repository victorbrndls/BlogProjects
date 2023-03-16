package com.victorbrandalise.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val backgroundColor = Color(49, 52, 58)
private val primaryColor = Color(68, 71, 70)
private val secondaryColor = Color(68, 71, 70)
private val selectedColor = Color(104, 220, 255, 255)

@Composable
fun TimerPicker() {
    var selectedPart by remember { mutableStateOf(TimePart.Hour) }
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Text(text = "Select time", color = Color.White)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            CompositionLocalProvider(LocalContentColor provides Color.White) {
                Card(
                    shape = RoundedCornerShape(6.dp),
                    backgroundColor = if (selectedPart == TimePart.Hour) selectedColor else secondaryColor,
                    modifier = Modifier.clickable { selectedPart = TimePart.Hour }
                ) {
                    val hour by remember {
                        derivedStateOf { if (selectedHour == 0) "00" else selectedHour.toString() }
                    }

                    Text(
                        text = hour,
                        fontSize = 26.sp,
                        color = if (selectedPart == TimePart.Hour) secondaryColor else Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = ":",
                    fontSize = 26.sp,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )

                Card(
                    shape = RoundedCornerShape(6.dp),
                    backgroundColor = if (selectedPart == TimePart.Minute) selectedColor else secondaryColor,
                    modifier = Modifier.clickable { selectedPart = TimePart.Minute }
                ) {
                    val minute by remember {
                        derivedStateOf { if (selectedMinute == 0) "00" else selectedMinute.toString() }
                    }

                    Text(
                        text = minute,
                        fontSize = 26.sp,
                        color = if (selectedPart == TimePart.Minute) secondaryColor else Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Clock(
            time = if (selectedPart == TimePart.Hour) selectedHour else selectedMinute,
            onTime = {
                if (selectedPart == TimePart.Hour) selectedHour = it else selectedMinute = it
            },
            modifier = Modifier
                .size(190.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.align(Alignment.End)
        ) {
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(56.dp)
                    .height(32.dp)
            ) {
                Text(
                    text = "Cancel",
                    fontSize = 10.sp,
                    color = selectedColor,
                )
            }
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(36.dp)
                    .height(32.dp)
            ) {
                Text(
                    text = "OK",
                    fontSize = 10.sp,
                    color = selectedColor
                )
            }
        }

    }
}

@Composable
fun Clock(
    time: Int,
    onTime: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var radiusPx by remember { mutableStateOf(0) }
    var radiusInsidePx by remember { mutableStateOf(0) }

    fun posX(hour: Int) =
        ((if (hour < 12) radiusPx else radiusInsidePx) * cos(angleForHour(hour))).toInt()

    fun posY(hour: Int) =
        ((if (hour < 12) radiusPx else radiusInsidePx) * sin(angleForHour(hour))).toInt()

    val content = @Composable {
        Hour(text = "00", hour = 0, isSelected = time == 0, onHour = onTime)
        Hour(text = "1", hour = 1, isSelected = time == 1, onHour = onTime)
        Hour(text = "2", hour = 2, isSelected = time == 2, onHour = onTime)
        Hour(text = "3", hour = 3, isSelected = time == 3, onHour = onTime)
        Hour(text = "4", hour = 4, isSelected = time == 4, onHour = onTime)
        Hour(text = "5", hour = 5, isSelected = time == 5, onHour = onTime)
        Hour(text = "6", hour = 6, isSelected = time == 6, onHour = onTime)
        Hour(text = "7", hour = 7, isSelected = time == 7, onHour = onTime)
        Hour(text = "8", hour = 8, isSelected = time == 8, onHour = onTime)
        Hour(text = "9", hour = 9, isSelected = time == 9, onHour = onTime)
        Hour(text = "10", hour = 10, isSelected = time == 10, onHour = onTime)
        Hour(text = "11", hour = 11, isSelected = time == 11, onHour = onTime)
        Hour(text = "12", hour = 12, isSelected = time == 12, onHour = onTime)
        Hour(text = "13", hour = 13, isSelected = time == 13, onHour = onTime)
        Hour(text = "14", hour = 14, isSelected = time == 14, onHour = onTime)
        Hour(text = "15", hour = 15, isSelected = time == 15, onHour = onTime)
        Hour(text = "16", hour = 16, isSelected = time == 16, onHour = onTime)
        Hour(text = "17", hour = 17, isSelected = time == 17, onHour = onTime)
        Hour(text = "18", hour = 18, isSelected = time == 18, onHour = onTime)
        Hour(text = "19", hour = 19, isSelected = time == 19, onHour = onTime)
        Hour(text = "20", hour = 20, isSelected = time == 20, onHour = onTime)
        Hour(text = "21", hour = 21, isSelected = time == 21, onHour = onTime)
        Hour(text = "22", hour = 22, isSelected = time == 22, onHour = onTime)
        Hour(text = "23", hour = 23, isSelected = time == 23, onHour = onTime)
//        Hour(text = "XII", hour = 0, onHour = { selectedHour = it })
//        Hour(text = "I", hour = 1, onHour = { selectedHour = it })
//        Hour(text = "II", hour = 2, onHour = { selectedHour = it })
//        Hour(text = "III", hour = 3, onHour = { selectedHour = it })
//        Hour(text = "IV", hour = 4, onHour = { selectedHour = it })
//        Hour(text = "V", hour = 5, onHour = { selectedHour = it })
//        Hour(text = "VI", hour = 6, onHour = { selectedHour = it })
//        Hour(text = "VII", hour = 7, onHour = { selectedHour = it })
//        Hour(text = "VIII", hour = 8, onHour = { selectedHour = it })
//        Hour(text = "IX", hour = 9, onHour = { selectedHour = it })
//        Hour(text = "X", hour = 10, onHour = { selectedHour = it })
//        Hour(text = "XI", hour = 11, onHour = { selectedHour = it })
//        Hour(text = "XII", hour = 12, onHour = { selectedHour = it })
//        Hour(text = "I", hour = 13, onHour = { selectedHour = it })
//        Hour(text = "II", hour = 14, onHour = { selectedHour = it })
//        Hour(text = "III", hour = 15, onHour = { selectedHour = it })
//        Hour(text = "IV", hour = 16, onHour = { selectedHour = it })
//        Hour(text = "V", hour = 17, onHour = { selectedHour = it })
//        Hour(text = "VI", hour = 18, onHour = { selectedHour = it })
//        Hour(text = "VII", hour = 19, onHour = { selectedHour = it })
//        Hour(text = "VIII", hour = 20, onHour = { selectedHour = it })
//        Hour(text = "IX", hour = 21, onHour = { selectedHour = it })
//        Hour(text = "X", hour = 22, onHour = { selectedHour = it })
//        Hour(text = "XI", hour = 23, onHour = { selectedHour = it })
    }

    Box(modifier = modifier) {
        Surface(
            color = primaryColor,
            shape = CircleShape,
            modifier = Modifier.fillMaxSize()
        ) {}

        val padding = 4.dp
        val hourCirclePx = 36f

        Layout(
            content = content,
            modifier = Modifier
                .padding(padding)
                .drawBehind {
                    val end = Offset(
                        x = size.width / 2 + posX(time),
                        y = size.height / 2 + posY(time)
                    )

                    drawCircle(
                        radius = 9f,
                        color = selectedColor,
                    )

                    drawLine(
                        start = center,
                        end = end,
                        color = selectedColor,
                        strokeWidth = 4f
                    )

                    drawCircle(
                        radius = hourCirclePx,
                        center = end,
                        color = selectedColor,
                    )
                }
        ) { measurables, constraints ->
            val placeables = measurables.map {
                it.measure(constraints)
            }
            assert(placeables.count() == 24) { "Missing hours: should be 24, is ${placeables.count()}" }

            layout(constraints.maxWidth, constraints.maxHeight) {
                val size = constraints.maxWidth
                val maxSize = maxOf(placeables.maxOf { it.width }, placeables.maxOf { it.height })

                radiusPx = (constraints.maxWidth - maxSize) / 2
                radiusInsidePx = (radiusPx * 0.67).toInt()

                placeables.forEachIndexed { index, placeable ->
                    placeable.place(
                        size / 2 - placeable.width / 2 + posX(index),
                        size / 2 - placeable.height / 2 + posY(index),
                    )
                }
            }
        }
    }
}

@Composable
fun Hour(
    text: String,
    hour: Int,
    isSelected: Boolean,
    onHour: (Int) -> Unit
) {
    Text(
        text = text,
        color = if (isSelected) secondaryColor else Color.White,
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { onHour(hour) }
        )
    )
}

enum class TimePart { Hour, Minute }

private const val step = PI * 2 / 12
private fun angleForHour(hour: Int) = -PI / 2 + step * hour

@Preview(device = Devices.PIXEL_4, showSystemUi = true)
@Composable
fun TimerPickerPreview() {
    Box(contentAlignment = Alignment.Center) {
        TimerPicker()
    }
}