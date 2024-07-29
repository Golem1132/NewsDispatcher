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
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import com.example.newsdispatcher.widgets.ElevatedText
import com.example.newsdispatcher.widgets.ElevatedTextField
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoScreen() {
    var nameInput by remember {
        mutableStateOf("")
    }
    var surnameInput by remember {
        mutableStateOf("")
    }
    var dateOfBirthInput by remember {
        mutableStateOf<Long?>(null)
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = dateOfBirthInput,
    )
    val dateFormatter = DatePickerDefaults.dateFormatter(
        selectedDateSkeleton = "dd/MM/yyyy",
        selectedDateDescriptionSkeleton = "dd/MM/yyyy"
    )
    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(text = "1/x") }) },
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
                        modifier = Modifier.clickable { },
                        fontSize = 5.em,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Next",
                        modifier = Modifier.clickable { },
                        fontSize = 5.em,
                        fontWeight = FontWeight.Bold
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
                value = nameInput, onTextChange = { newText ->
                    nameInput = newText
                }, singleLine = true,
                placeholder = {
                    Text("Name")
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
                value = surnameInput, onTextChange = { newText ->
                    surnameInput = newText
                }, singleLine = true,
                placeholder = {
                    Text("Surname")
                }
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .weight(1f, false)
            )
            ElevatedText(
                modifier = Modifier.clickable {
                    isDialogVisible = true
                },
                text = if (dateOfBirthInput == null)
                    "Birthdate"
                else
                    dateFormatter.formatDate(dateOfBirthInput, Locale.getDefault()) ?: "Birthdate"
            )
            if (isDialogVisible) {

                DatePickerDialog(
                    onDismissRequest = {
                        dateOfBirthInput = datePickerState.selectedDateMillis ?: 0L
                        isDialogVisible = false
                    },
                    confirmButton = {
                        Button(onClick = {
                            dateOfBirthInput = datePickerState.selectedDateMillis ?: 0L
                            isDialogVisible = false
                        }) {
                            Text(text = "Confirm")
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState,
                        dateFormatter = dateFormatter
                    )
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewPersonalInfoScreen() {
    PersonalInfoScreen()
}