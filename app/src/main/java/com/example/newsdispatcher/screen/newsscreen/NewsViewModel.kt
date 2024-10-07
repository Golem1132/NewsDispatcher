package com.example.newsdispatcher.screen.newsscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.newsdispatcher.api.NewsDispatcherClient
import com.example.newsdispatcher.api.service.NewsService
import com.example.newsdispatcher.data.NewsDispatcherMediator
import com.example.newsdispatcher.database.NewsDispatcherDatabase
import com.example.newsdispatcher.database.data.SearchHistory
import com.example.newsdispatcher.utils.NewsScreenEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(androidx.paging.ExperimentalPagingApi::class)
class NewsViewModel(private val service: NewsService, private val db: NewsDispatcherDatabase) :
    ViewModel() {

    private val _currentCategory = MutableStateFlow<String>("general")
    val currentCategory = _currentCategory.asStateFlow()
    private var _currentPager = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = NewsDispatcherMediator(
            _currentCategory.value,
            { page ->
                service.getTopHeadlinesPaged(_currentCategory.value, page)
            }, db
        )
    ) {
        db.getArticleEntryDao().getAllArticlesByCategory(_currentCategory.value)
    }
    private val _currentDataset = MutableStateFlow(
        _currentPager.flow.cachedIn(viewModelScope)
    )
    val currentDataset = _currentDataset.asStateFlow()

    private val _uiEvent = MutableStateFlow<NewsScreenEvent>(NewsScreenEvent.LOADING)
    val uiEvent = _uiEvent.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<SearchHistory>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    init {
        viewModelScope.launch {
            db.getSearchHistoryDao().getAll().collect {
                _searchHistory.emit(it)
            }
        }

    }

    fun setCategory(newCategory: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentPager = Pager(
                config = PagingConfig(pageSize = 10),
                remoteMediator = NewsDispatcherMediator(
                    newCategory,
                    { page ->
                        service.getTopHeadlinesPaged(_currentCategory.value, page)
                    }, db
                )
            ) {
                db.getArticleEntryDao().getAllArticlesByCategory(newCategory)
            }
            db.getNewsDispatcherRemoteKeysDao().deleteAllKeys()
            _currentDataset.emit(_currentPager.flow.cachedIn(viewModelScope))
            _currentCategory.emit(newCategory)
        }
    }

    fun insertNewSearch(newSearchHistory: SearchHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            db.getSearchHistoryDao().insert(newSearchHistory)
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