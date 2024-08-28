package com.example.newsdispatcher.screen.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsdispatcher.R
import com.example.newsdispatcher.widgets.ElevatedTextField

@Composable
fun LoginScreen(navController: NavHostController) {
    var loginText by rememberSaveable {
        mutableStateOf("")
    }
    var passwordText by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .weight(1f, false)
                    .clip(CircleShape)
                    .background(color = Color.Blue),
                contentScale = ContentScale.Inside,
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "NewsDispatcher logo"
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .weight(1f, false)
            )
            ElevatedTextField(
                modifier = Modifier
                    .weight(1f, false),
                value = loginText, onTextChange = {
                    loginText = it
                }, singleLine = true
            ) {
                Text(text = "E-mail")
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .weight(1f, false)
            )
            ElevatedTextField(
                modifier = Modifier
                    .weight(1f, false),
                value = passwordText, onTextChange = {
                    passwordText = it
                }, singleLine = true
            ) {
                Text(text = "Password")
            }
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .weight(1f, false)
            )

            Button(
                modifier = Modifier.weight(1f, false),
                onClick = {
                    navController.navigate("feed")
                }) {
                Text(text = "Log in")
            }
        }
        val clickableAnnotatedString = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 1.25.em
                )
            ) {
                append("Need an account? ")
            }
            pushStringAnnotation("route", "Put route here")
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold, color = Color.Blue,
                    fontSize = 1.25.em
                )
            ) {
                append("Make one!")
            }
            pop()
        }
        ClickableText(text = clickableAnnotatedString) { offset ->
            clickableAnnotatedString.getStringAnnotations("route", offset, offset).firstOrNull()
                ?.let {
                    navController.navigate("createAccount")
                }
        }

    }
}

@PreviewScreenSizes
@Composable
fun PreviewLoginScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        LoginScreen(rememberNavController())
    }
}