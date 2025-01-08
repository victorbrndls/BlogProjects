package com.victorbrandalise

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomMenuBar(
    screens: List<Screen>,
    currentScreen: Screen?,
    onNavigateTo: (Screen) -> Unit,
) {
    val backgroundShape = remember { menuBarShape() }

    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White, backgroundShape)
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            FloatingActionButton(
                shape = RoundedCornerShape(50),
                containerColor = Color.White,
                contentColor = Color.Gray,
                onClick = {},
                modifier = Modifier.clip(RoundedCornerShape(50))
            ) {
                Row(
                    modifier = Modifier.size(64.dp)
                ) {
                    BottomBarItem(screens[2], currentScreen, onNavigateTo)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        Row(
            modifier = Modifier
                .height(56.dp)
                .align(Alignment.BottomCenter)
        ) {
            BottomBarItem(screens[0], currentScreen, onNavigateTo)
            BottomBarItem(screens[1], currentScreen, onNavigateTo)

            Spacer(modifier = Modifier.width(72.dp))

            BottomBarItem(screens[3], currentScreen, onNavigateTo)
            BottomBarItem(screens[4], currentScreen, onNavigateTo)
        }
    }
}

@Composable
private fun RowScope.BottomBarItem(
    screen: Screen,
    currentScreen: Screen?,
    onNavigateTo: (Screen) -> Unit,
) {
    val selected = currentScreen?.route == screen.route

    Box(
        Modifier
            .selectable(
                selected = selected,
                onClick = { onNavigateTo(screen) },
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = remember { ripple(radius = 32.dp) }
            )
            .fillMaxHeight()
            .weight(1f),
        contentAlignment = Alignment.Center
    ) {
        BadgedBox(
            badge = {},
            content = {
                Image(
                    painter = painterResource(
                        id = when {
                            selected -> screen.selectedIcon
                            else -> screen.icon
                        }
                    ),
                    contentDescription = null
                )
            },
        )
    }
}

private fun menuBarShape() = GenericShape { size, _ ->
    reset()

    moveTo(0f, 0f)

    val width = 150f
    val height = 90f

    val point1 = 75f
    val point2 = 85f

    lineTo(size.width / 2 - width, 0f)

    cubicTo(
        size.width / 2 - point1, 0f,
        size.width / 2 - point2, height,
        size.width / 2, height
    )

    cubicTo(
        size.width / 2 + point2, height,
        size.width / 2 + point1, 0f,
        size.width / 2 + width, 0f
    )

    lineTo(size.width / 2 + width, 0f)

    lineTo(size.width, 0f)
    lineTo(size.width, size.height)
    lineTo(0f, size.height)

    close()
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    var currentScreen by remember { mutableStateOf<Screen?>(null) }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        BottomMenuBar(
            screens = listOf(
                Screen(
                    route = "home",
                    icon = R.drawable.outline_home_24,
                    selectedIcon = R.drawable.baseline_home_24,
                ),
                Screen(
                    route = "products",
                    icon = R.drawable.outline_collections_24,
                    selectedIcon = R.drawable.baseline_collections_24,
                ),
                Screen(
                    route = "cart",
                    icon = R.drawable.outline_shopping_cart_24,
                    selectedIcon = R.drawable.baseline_shopping_cart_24,
                ),
                Screen(
                    route = "profile",
                    icon = R.drawable.outline_person_24,
                    selectedIcon = R.drawable.baseline_person_24,
                ),
                Screen(
                    route = "chat",
                    icon = R.drawable.outline_chat_24,
                    selectedIcon = R.drawable.baseline_chat_24,
                ),
            ),
            currentScreen = currentScreen,
            onNavigateTo = { currentScreen = it },
        )
    }
}

data class Screen(
    val route: String,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int,
)