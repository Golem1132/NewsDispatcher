package com.example.newsdispatcher.bottombar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em

@Composable
fun WelcomeScreenBottomBar(
    onSkip: () -> Unit,
    onNext: () -> Unit,
) {
    BottomAppBar(containerColor = Color.Transparent) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.clickable(onClick = onSkip),
                text = "Skip", fontSize = 5.em, fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.clickable(onClick = onNext),
                text = "Next", fontSize = 5.em, fontWeight = FontWeight.Bold
            )
        }
    }
}