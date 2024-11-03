package com.example.newsdispatcher.screen.newsscreen

import android.content.Intent
import android.icu.util.Calendar
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.newsdispatcher.appbar.NewsTopAppBar
import com.example.newsdispatcher.database.data.SearchHistory
import com.example.newsdispatcher.navigation.SearchScreenRoutes
import com.example.newsdispatcher.navigation.WebViewRoutes
import com.example.newsdispatcher.utils.NewsCategories
import com.example.newsdispatcher.utils.NewsScreenEvent
import com.example.newsdispatcher.widgets.HistoryItem
import com.example.newsdispatcher.widgets.NewsCard
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun NewsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: NewsViewModel = viewModel(
        factory = NewsViewModel.provideFactory(LocalContext.current)
    )
    val currentCategory = viewModel.currentCategory.collectAsState()
    val currentNews = viewModel.currentDataset.collectAsState().value.collectAsLazyPagingItems()
    val currentUiState = viewModel.uiEvent.collectAsState()
    var isSearchActive by remember {
        mutableStateOf(false)
    }
    var searchQuery by remember {
        mutableStateOf("")
    }
    val currentSearchHistory = viewModel.searchHistory.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            NewsTopAppBar(navController = navController,
                isActive = isSearchActive,
                query = searchQuery,
                onActiveChange = {
                    isSearchActive = it
                },
                onQueryChange = {
                    searchQuery = it
                },
                onKeyboardAction = {
                    viewModel.insertNewSearch(
                        SearchHistory(
                            query = searchQuery,
                            time = Calendar.getInstance().timeInMillis
                        )
                    )
                    navController.navigate("${SearchScreenRoutes.SEARCH_SCREEN}/$searchQuery")
                },
                onTrailingIconClick = {
                    searchQuery = ""
                    focusManager.clearFocus()
                    isSearchActive = false
                },
                hasAction = true)
        }, snackbarHost = {
            if (currentUiState.value == NewsScreenEvent.ERROR) {
                Snackbar {
                    Text(text = "Error has occurred")
                }
            }
        }
    ) {
        AnimatedContent(targetState = isSearchActive, label = "XD") { isActive ->
            when (isActive) {
                true -> {
                    Column(modifier = Modifier.padding(it)) {
                        Text(text = "Recent search history")
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(currentSearchHistory.value) {
                                HistoryItem(searchHistory = it,
                                    onClick = {
                                        viewModel.insertNewSearch(
                                            SearchHistory(
                                                query = it.query,
                                                time = Calendar.getInstance().timeInMillis
                                            )
                                        )
                                        navController.navigate("${SearchScreenRoutes.SEARCH_SCREEN}/${it.query}")
                                    },
                                    onDeleteClick = {
                                        viewModel.deleteSearchEntry(it)
                                    })
                            }
                        }
                    }
                }

                false -> {
                    Column {
                        LazyRow(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxWidth()
                                .height(48.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                5.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            contentPadding = PaddingValues(horizontal = 5.dp)
                        ) {
                            items(NewsCategories.getAll()) { category ->
                                if (category.id != null && category.title != null)
                                    Box(
                                        modifier = Modifier
                                            .defaultMinSize(minWidth = 100.dp)
                                            .clip(RoundedCornerShape(20))
                                            .background(
                                                color =
                                                if (currentCategory.value == category.id)
                                                    MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.inversePrimary,
                                                shape = RoundedCornerShape(20)
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
                            items(
                                count = currentNews.itemCount,
                                key = currentNews.itemKey { article -> article.id },
                                contentType = currentNews.itemContentType { "Articles" }
                            ) { index ->
                                val item = currentNews[index]!!.article
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    NewsCard(
                                        modifier = Modifier,
                                        item,
                                        onClick = {
                                            val url = URLEncoder.encode(
                                                item.url,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate("${WebViewRoutes.WEB_VIEW_SCREEN}/$url")
                                        },
                                        onSaved = {
                                            viewModel.doOnSaved(item)
                                        },
                                        onShare = { url ->
                                            val intent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(Intent.EXTRA_STREAM, url)
                                                type = "text/plain"
                                            }
                                            val shareIntent = Intent.createChooser(intent, null)
                                            context.startActivity(shareIntent)
                                        }
                                    )
                                    if (currentNews.itemCount - 1 == index &&
                                        currentNews.loadState.mediator?.append is LoadState.Loading
                                    )
                                        CircularProgressIndicator(modifier = Modifier)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    BackHandler(isSearchActive) {
        isSearchActive = false
        searchQuery = ""
        focusManager.clearFocus()

    }
}

@Preview
@Composable
fun PreviewNewsScreen() {
    NewsScreen(rememberNavController())
}