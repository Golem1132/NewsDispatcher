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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsdispatcher.navigation.CreateAccountRoutes
import com.example.newsdispatcher.widgets.ElevatedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialsScreen(navController: NavHostController, currentPage: Int?, maxPage: Int?) {
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
            if (currentPage != null && maxPage != null)
                CenterAlignedTopAppBar(title = { Text(text = "$currentPage/$maxPage") })
        },
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        },
                        fontSize = 5.em,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate(CreateAccountRoutes.PERSONAL_INFO_SCREEN)
                        }),
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
                text = "Create account", fontSize = 20.sp
            )
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
    CredentialsScreen(rememberNavController(), null, null)
}