package com.example.newsdispatcher.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ElevatedText(
    modifier: Modifier = Modifier,
    textAlignment: Alignment = Alignment.CenterStart,
    text: String
) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .defaultMinSize(280.dp, 56.dp)
            .then(modifier),
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(20)
    ) {
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .width(IntrinsicSize.Max)
                .padding(8.dp),
            contentAlignment = textAlignment
        ) {
            Text(text = text)
        }
    }
}