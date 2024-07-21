package com.example.newsdispatcher.screen.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsdispatcher.R
import com.example.newsdispatcher.widgets.ElevatedTextField

@Composable
fun LoginScreen() {
    var loginText by rememberSaveable {
        mutableStateOf("")
    }
    var passwordText by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(color = Color.Blue),
            contentScale = ContentScale.Inside,
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "NewsDispatcher logo"
        )
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedTextField(
            value = loginText, onTextChange = {
                loginText = it
            }, singleLine = true
        ) {
            Text(text = "Label here")
        }
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedTextField(value = passwordText, onTextChange = {
            passwordText = it
        }, singleLine = true) {
            Text(text = "Label here")
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Log in")
        }

    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        LoginScreen()
    }
}