package com.example.newsdispatcher.screen.newsscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.newsdispatcher.NewsDispatcherMediator
import com.example.newsdispatcher.api.NewsDispatcherClient
import com.example.newsdispatcher.api.service.NewsService
import com.example.newsdispatcher.database.NewsDispatcherDatabase
import com.example.newsdispatcher.utils.NewsScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(androidx.paging.ExperimentalPagingApi::class)
class NewsViewModel(private val service: NewsService, private val db: NewsDispatcherDatabase) :
    ViewModel() {

    private val _currentCategory = MutableStateFlow<String?>(null)
    val currentCategory = _currentCategory.asStateFlow()

    val currentDataset = Pager(
    config = PagingConfig(pageSize = 10),
    remoteMediator = NewsDispatcherMediator(service, db)
    ) {
        db.getArticleEntryDao().getAllArticles()
    }.flow.cachedIn(viewModelScope)

    private val _uiEvent = MutableStateFlow<NewsScreenEvent>(NewsScreenEvent.LOADING)
    val uiEvent = _uiEvent.asStateFlow()

    init {

    }

/*init {
    viewModelScope.launch(Dispatchers.IO) {
        db.getArticleEntryDao().getAllArticlesAsFlow()
            .collect { collected ->
                _currentDataset.emit(collected)
            }
    }
    viewModelScope.launch(Dispatchers.IO) {
        service.getTopHeadlines(
            _currentCategory.value,
            10,
            1
        ).onSuccess {
            launch {
                if (it.status == "ok") {
                    db.getArticleEntryDao().clearTable()
                    db.getArticleEntryDao().insertArticles(it.articles.map { article ->
                        ArticleEntry(
                            article = article
                        )
                    })
                }
                _uiEvent.emit(NewsScreenEvent.IDLE)
            }
        }.onError {
            launch {
                _uiEvent.emit(NewsScreenEvent.ERROR)
                delay(5.seconds)
                _uiEvent.emit(NewsScreenEvent.IDLE)
            }
            it.printStackTrace()
        }
    }
}*/

fun setCategory(newCategory: String?) {
    viewModelScope.launch {
        _currentCategory.emit(newCategory)
        service.getTopHeadlines(
            _currentCategory.value,
            10,
            1
        ).onSuccess {
            launch {
                if (it.status == "ok") {
//                    _currentDataset.emit(it.articles.toList())
                }
            }
        }.onError {
            it.printStackTrace()
        }

    }
}

companion object {
    fun provideFactory(context: Context) = viewModelFactory {
        initializer {
            val service = NewsDispatcherClient().getNewsService()
            val db = NewsDispatcherDatabase.getInstance(context)
            NewsViewModel(service, db)
        }
    }
}

}