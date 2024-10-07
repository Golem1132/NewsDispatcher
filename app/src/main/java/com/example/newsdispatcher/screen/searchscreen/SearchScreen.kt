package com.example.newsdispatcher.screen.searchscreen

import android.icu.util.Calendar
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newsdispatcher.R
import com.example.newsdispatcher.database.data.SearchHistory
import com.example.newsdispatcher.navigation.AccountRoutes
import com.example.newsdispatcher.navigation.WebViewRoutes
import com.example.newsdispatcher.widgets.NewsCard
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel: SearchScreenViewModel = viewModel(
        factory = SearchScreenViewModel.provideFactory(LocalContext.current)
    )
    val currentNews = viewModel.currentDataset.collectAsState().value.collectAsLazyPagingItems()
    var isSearchActive by remember {
        mutableStateOf(false)
    }
    val searchQuery = viewModel.searchQuery.collectAsState()
    val currentSearchHistory = viewModel.searchHistory.collectAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = if (isSearchActive) 0.dp else 16.dp)
                    .height(64.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .animateContentSize(
                            animationSpec = tween(5000, easing = LinearOutSlowInEasing)
                        )
                        .onFocusChanged {
                            isSearchActive = it.isFocused
                        },
                    placeholder = {
                        Text(text = "What are you looking for?")
                    },
                    shape = if (isSearchActive) RectangleShape else CircleShape,
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = ""
                        )
                    },
                    trailingIcon = {
                        if (isSearchActive)
                            Icon(imageVector = Icons.Default.Close, contentDescription = "")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.insertNewSearch(
                            SearchHistory(
                                query = searchQuery.value,
                                time = Calendar.getInstance().timeInMillis
                            )
                        )
                        currentNews.refresh()
                    }),
                    value = searchQuery.value, onValueChange = {
                        viewModel.updateQuery(it)
                    }
                )
                Crossfade(
                    targetState = isSearchActive,
                    label = "Change icons on search bar active"
                ) { isActive ->
                    if (!isActive) {
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .weight(1f)
                                .clickable {
                                    navController.navigate(AccountRoutes.ROUTE)
                                },
                            model = "", contentDescription = ""
                        ) {
                            when (painter.state) {
                                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                                else -> Icon(
                                    modifier = Modifier.size(30.dp),
                                    painter = painterResource(id = R.drawable.baseline_person_24),
                                    contentDescription = "Placeholder for no photo or error"
                                )
                            }
                        }
                    }
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
                                Text(text = it.query)
                            }
                        }
                    }
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
                                        false,
                                        onClick = {
                                            val url = URLEncoder.encode(
                                                item.url,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate("${WebViewRoutes.WEB_VIEW_SCREEN}/$url")
                                        },
                                        onSaved = {

                                        },
                                        onShare = { url ->

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
        viewModel.updateQuery("")
        focusManager.clearFocus()

    }
}