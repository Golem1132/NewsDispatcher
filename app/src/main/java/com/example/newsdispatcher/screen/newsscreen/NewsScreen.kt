package com.example.newsdispatcher.screen.newsscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newsdispatcher.R
import com.example.newsdispatcher.navigation.AccountRoutes
import com.example.newsdispatcher.navigation.WebViewRoutes
import com.example.newsdispatcher.utils.NewsCategories
import com.example.newsdispatcher.widgets.NewsCard
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavHostController) {
    val viewModel: NewsViewModel = viewModel(
        factory = NewsViewModel.provideFactory()
    )
    val currentCategory = viewModel.currentCategory.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
            },
                actions = {
                    SubcomposeAsyncImage(
                        modifier = Modifier.clickable {
                            navController.navigate(AccountRoutes.ROUTE)
                        },
                        model = "", contentDescription = ""
                    ) {
                        when (painter.state) {
                            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                            is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                            else -> Icon(
                                modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max),
                                painter = painterResource(id = R.drawable.baseline_person_24),
                                contentDescription = "Placeholder for no photo or error"
                            )
                        }
                    }
                }
            )
        }
    ) {
        Column {
            LazyRow(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(horizontal = 5.dp)
            ) {
                items(NewsCategories.getAll()) { category ->
                    Box(
                        modifier = Modifier
                            .defaultMinSize(minWidth = 100.dp)
                            .clip(RoundedCornerShape(20))
                            .background(
                                color =
                                if (currentCategory.value == category.id)
                                    MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.inversePrimary, shape = RoundedCornerShape(20)
                            )
                            .padding(10.dp)
                            .clickable {
                                viewModel.setCategory(category.id)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = stringResource(id = category.title))
                    }
                }
            }
            LazyVerticalGrid(
                modifier = Modifier.padding(
                    start = it.calculateStartPadding(LayoutDirection.Ltr) + 10.dp,
                    top = 10.dp,
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
                        navController.navigate("${WebViewRoutes.WEB_VIEW_SCREEN}/$url")
                    }
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