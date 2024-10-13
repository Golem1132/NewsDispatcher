package com.example.newsdispatcher.screen.searchscreen

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.newsdispatcher.api.NewsDispatcherClient
import com.example.newsdispatcher.api.service.NewsService
import com.example.newsdispatcher.data.NewsDispatcherMediator
import com.example.newsdispatcher.database.NewsDispatcherDatabase
import com.example.newsdispatcher.database.data.SavedEntry
import com.example.newsdispatcher.database.data.SearchHistory
import com.example.newsdispatcher.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchScreenViewModel(
    savedState: SavedStateHandle,
    private val db: NewsDispatcherDatabase,
    private val service: NewsService
) : ViewModel() {

    var searchQuery = (savedState["initialSearch"]) ?: ""

    @OptIn(ExperimentalPagingApi::class)
    private var _currentPager = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = NewsDispatcherMediator(
            "",
            { page -> service.getEverything(searchQuery, page) }, db
        )
    ) {
        db.getArticleEntryDao().getAllArticlesByCategory("")
    }
    private val _currentDataset = MutableStateFlow(
        _currentPager.flow.cachedIn(viewModelScope)
    )
    val currentDataset = _currentDataset.asStateFlow()
    private val _searchHistory = MutableStateFlow<List<SearchHistory>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    init {
        viewModelScope.launch {
            db.getSearchHistoryDao().getAll().collect {
                _searchHistory.emit(it)
            }
        }

    }

    fun insertNewSearch(newSearchHistory: SearchHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            db.getSearchHistoryDao().insert(newSearchHistory)
        }
    }

    fun updateQuery(newQuery: String) {
        searchQuery = newQuery
    }

    fun doOnSaved(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            val isInDb = db.getSavedEntryDao().find(article) != null
            if (isInDb)
                db.getSavedEntryDao().delete(article)
            else
                db.getSavedEntryDao().insert(SavedEntry(article = article))
        }
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val service = NewsDispatcherClient().getNewsService()
                    val db = NewsDispatcherDatabase.getInstance(context)
                    SearchScreenViewModel(createSavedStateHandle(), db, service)
                }
            }

        }
    }
}