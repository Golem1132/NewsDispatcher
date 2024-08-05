package com.example.newsdispatcher.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SourceTile(
    modifier: Modifier = Modifier,
    isPicked: Boolean = false
) {
    Surface(
        modifier = Modifier
            .defaultMinSize(24.dp, 24.dp)
            .then(modifier)
            .then(
                if (isPicked)
                    Modifier.border(2.dp, Color.Green, RoundedCornerShape(20))
                else Modifier
            ),
        shape = RoundedCornerShape(20),
        shadowElevation = 2.dp,
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            model = "https://www.google.com/s2/favicons?domain=${"https://abcnews.go.com"}&sz=256",
            contentDescription = ""
        )
    }
}