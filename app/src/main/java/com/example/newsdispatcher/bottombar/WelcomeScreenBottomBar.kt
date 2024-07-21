package com.example.newsdispatcher.bottombar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WelcomeScreenBottomBar(
    onSkip: () -> Unit,
    onNext: () -> Unit,
) {
    BottomAppBar() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.clickable (onClick = onSkip),
                text = "Skip")
            Text(
                modifier = Modifier.clickable (onClick = onNext),
                text = "Next")
        }
    }
}