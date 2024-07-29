package com.example.newsdispatcher.screen.welcomescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.newsdispatcher.bottombar.WelcomeScreenBottomBar
import com.example.newsdispatcher.widgets.ElevatedText

private val languageHashMap: HashMap<String,String> = hashMapOf(
    "ar" to "Arabic",
    "de" to "German",
    "en" to "English",
    "es" to "Spanish",
    "fr" to "French",
    "he" to "Hebrew",
    "it" to "Italian",
    "nl" to "Dutch",
    "no" to "Norwegian",
    "pt" to "Portugal",
    "ru" to "Russian",
    "sv" to "Swedish",
    "ud" to "",
    "zh" to "Chinese"
)
private val languageArray = arrayOf<String>(
    "English",
    "French",
    "Spanish"
)

@Composable
fun PickLanguagesScreen() {
    var showDialog by remember {
        mutableStateOf(false)
    }
    val pickedLanguages = remember {
        mutableStateListOf<String>()
    }
    Scaffold(
        bottomBar = {
            WelcomeScreenBottomBar(
                onNext = {},
                onSkip = {}
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hi! Tell us more about yourself",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Preferred language",
                style = MaterialTheme.typography.headlineSmall
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(pickedLanguages) {
                    ElevatedText(text = it)
                }
            }
            Button(
                shape = CircleShape,
                onClick = {
                    showDialog = true
                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add button")
            }

        }
        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(20))
                        .padding(8.dp)
                ) {
                    LazyColumn {
                        items(languageArray) {
                            Text(text = it, modifier = Modifier.clickable {
                                pickedLanguages.add(it)
                                showDialog = false
                            })
                        }
                    }
                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun PreviewWelcomeScreen() {
    PickLanguagesScreen()
}