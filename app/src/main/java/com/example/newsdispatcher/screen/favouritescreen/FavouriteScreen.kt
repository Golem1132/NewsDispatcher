package com.example.newsdispatcher.screen.favouritescreen

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.newsdispatcher.appbar.NewsTopAppBar
import com.example.newsdispatcher.model.Article
import com.example.newsdispatcher.navigation.WebViewRoutes
import com.example.newsdispatcher.widgets.NewsCard
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FavouriteScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: FavouriteViewModel = viewModel(
        factory = FavouriteViewModel.provideFactory(context)
    )
    val savedArticles = viewModel.data.collectAsState()
    var isSearchActive by remember {
        mutableStateOf(false)
    }
    val searchQuery = viewModel.searchQuery.collectAsState()
    var deleteDialogActive by remember {
        mutableStateOf(false)
    }
    var clickedItem: Article? = null
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            NewsTopAppBar(
                navController = navController,
                isActive = isSearchActive,
                query = searchQuery.value,
                onActiveChange = {
                    isSearchActive = it
                },
                onQueryChange = {
                    viewModel.updateQuery(it)
                },
                onKeyboardAction = {
                    viewModel.updateQuery(searchQuery.value)
                    viewModel.performSearch()
                    isSearchActive = false
                },
                onTrailingIconClick = {
                    viewModel.updateQuery(searchQuery.value)
                    focusManager.clearFocus()
                    isSearchActive = false
                },
                hasAction = false
            )
        }
    ) {
        AnimatedContent(targetState = isSearchActive, label = "XD") { isActive ->
            when (isActive) {
                true -> {
                    Column(modifier = Modifier.padding(it)) {}
                }

                false -> {
                    Column(modifier = Modifier.padding(it)) {
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
                                savedArticles.value
                            ) { item ->
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
                                            clickedItem = item
                                            deleteDialogActive = true
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
                                }
                            }
                        }
                    }
                    if (deleteDialogActive)
                        AlertDialog(
                            onDismissRequest = { deleteDialogActive = false },
                            confirmButton = {
                                Text(
                                    modifier = Modifier.clickable {
                                        if (clickedItem != null)
                                            viewModel.delete(clickedItem!!)
                                        else
                                            deleteDialogActive = false
                                    },
                                    text = "Yes"
                                )
                            },
                            text = {
                                Text(text = "Delete article?")
                            }
                        )
                }
            }
        }
    }
    BackHandler(isSearchActive) {
        isSearchActive = false
        viewModel.updateQuery(searchQuery.value)
        focusManager.clearFocus()
    }
}

