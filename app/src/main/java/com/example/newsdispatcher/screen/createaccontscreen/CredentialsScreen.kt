package com.example.newsdispatcher.screen.createaccontscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.newsdispatcher.widgets.ElevatedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialsScreen() {
    var emailInput by remember {
        mutableStateOf("")
    }
    var passwordInput by remember {
        mutableStateOf("")
    }
    var repeatPassword by remember {
        mutableStateOf("")
    }
    val isPasswordEqual by remember(passwordInput, repeatPassword) {
        mutableStateOf(passwordInput != repeatPassword)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "1/x") })
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier.clickable(onClick = { }),
                        text = "Next", fontSize = 5.em, fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .weight(1f, false),
                text = "Create account", fontSize = 20.sp)
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .weight(1f, false)
            )
            ElevatedTextField(
                modifier = Modifier
                    .weight(1f, false),
                value = emailInput, onTextChange = { newText ->
                    emailInput = newText
                }, singleLine = true,
                placeholder = {
                    Text("E-mail")
                }
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .weight(1f, false)
            )
            ElevatedTextField(
                modifier = Modifier
                    .weight(1f, false),
                value = passwordInput, onTextChange = { newText ->
                    passwordInput = newText
                }, singleLine = true,
                placeholder = {
                    Text("Password")
                }
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .weight(1f, false)
            )
            ElevatedTextField(
                modifier = Modifier
                    .weight(1f, false),
                value = repeatPassword, onTextChange = { newText ->
                    repeatPassword = newText
                }, singleLine = true,
                error = isPasswordEqual,
                placeholder = {
                    Text("Repeat password")
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewCredentialsScreen() {
    CredentialsScreen()
}