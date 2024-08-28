package com.example.newsdispatcher.screen.newsscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newsdispatcher.R
import com.example.newsdispatcher.widgets.NewsCard
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { }, actions = {
                SubcomposeAsyncImage(
                    modifier = Modifier.clickable {
                        navController.navigate("account")
                    },
                    model = "", contentDescription = "") {
                    when (painter.state) {
                        is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                        is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                        else -> Icon(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = "Placeholder for no photo or error"
                        )
                    }
                }
            })
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(
                start = it.calculateStartPadding(LayoutDirection.Ltr) + 10.dp,
                top = it.calculateTopPadding() + 10.dp,
                end = it.calculateEndPadding(LayoutDirection.Ltr) + 10.dp,
                bottom = it.calculateBottomPadding() + 10.dp
            ),
            columns = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(10) {
                NewsCard(modifier = Modifier) {
                    val url = URLEncoder.encode(
                        "https://www.google.pl",
                        StandardCharsets.UTF_8.toString()
                    )
                    navController.navigate("webView/$url")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewNewsScreen() {
    NewsScreen(rememberNavController())
}