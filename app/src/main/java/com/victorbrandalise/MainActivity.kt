package com.victorbrandalise

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                content = {
                    Column {
                        MarqueeText(text = "This is my first text, This is my first text, This is my first text, This is my first text,")
                        Marquee {
                            Row {
                                Text(text = "This is my 1st text")
                                Icon(Icons.Default.KeyboardArrowRight, contentDescription = "")
                                Text(text = "This is my 2nd text")
                                Box(modifier = Modifier.size(16.dp).background(Color.Black))
                                Text(text = "This is my 3rd text")
                            }
                        }
                    }
                }
            )
        }
    }

}