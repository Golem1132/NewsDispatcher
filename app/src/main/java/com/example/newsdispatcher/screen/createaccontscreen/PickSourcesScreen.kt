package com.example.newsdispatcher.screen.createaccontscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.newsdispatcher.navigation.LoginRoutes
import com.example.newsdispatcher.widgets.SourceTile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickSourcesScreen(navController: NavHostController, currentPage: Int?, maxPage: Int?) {
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
                        text = "Finish",
                        modifier = Modifier.clickable {
                            navController.navigate(LoginRoutes.LOGIN_SCREEN)
                        },
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
                modifier = Modifier,
                text = "Pick favorite sources", fontSize = 20.sp
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 10.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                items(10) {
                    SourceTile(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPickSourcesScreen() {
    PickSourcesScreen(rememberNavController(), null, null)
}