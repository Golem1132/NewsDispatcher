package com.example.newsdispatcher.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun ElevatedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onTextChange: (String) -> Unit,
    singleLine: Boolean = false,
    readOnly: Boolean = false,
    placeholder: @Composable () -> Unit = {}
) {

    BasicTextField(
        modifier = modifier
            .padding(8.dp)
            .defaultMinSize(280.dp, 56.dp),
        textStyle = LocalTextStyle.current,
        value = value,
        onValueChange = onTextChange,
        singleLine = singleLine,
        readOnly = readOnly,
        decorationBox = {
            //Container
            Box(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .width(IntrinsicSize.Max)
                    .shadow(
                        1.dp, RoundedCornerShape(20),
                        false
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            1.dp
                        ), shape = RoundedCornerShape(20)
                    )
                    .clip(RoundedCornerShape(20))
                    .padding(start = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                //label
                if (value.isEmpty())
                    placeholder()
                it()
            }
        })

}