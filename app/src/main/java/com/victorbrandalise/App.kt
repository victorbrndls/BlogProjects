package com.victorbrandalise

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            var showDetails by remember { mutableStateOf(false) }

            SharedTransitionLayout {
                AnimatedContent(
                    showDetails,
                    label = "basic_transition"
                ) { targetState ->
                    Box(Modifier.fillMaxSize()) {
                        if (!targetState) {
                            LessonCard(
                                { showDetails = true },
                                this@SharedTransitionLayout,
                                this@AnimatedContent
                            )
                        } else {
                            LessonDetail(
                                { showDetails = false },
                                this@SharedTransitionLayout,
                                this@AnimatedContent
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun LessonCard(
    showDetails: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .padding(12.dp)
                .clickable { showDetails() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Lesson 1",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = "title"),
                            animatedVisibilityScope = animatedContentScope
                        )
                )
                Text(
                    text = "Let's learn about Compose",
                    fontSize = 14.sp,
                    modifier = Modifier.sharedBounds(
                        rememberSharedContentState(key = "subtitle"),
                        animatedVisibilityScope = animatedContentScope
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun LessonDetail(
    hideDetails: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    with(sharedTransitionScope) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            Text(
                text = "Lesson 1",
                fontSize = 35.sp,
                modifier = Modifier
                    .clickable { hideDetails() }
                    .sharedBounds(
                        rememberSharedContentState(key = "title"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(vertical = 16.dp)
            )
            Text(
                text = "Jetpack Compose is a modern toolkit designed to simplify and accelerate UI development on Android. As a declarative UI framework, it allows developers to build responsive and dynamic user interfaces with less code and more intuitive design principles. Here's an in-depth look at Compose and how it can help you create better layouts",
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedBounds(
                        rememberSharedContentState(key = "subtitle"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(12.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    App()
}