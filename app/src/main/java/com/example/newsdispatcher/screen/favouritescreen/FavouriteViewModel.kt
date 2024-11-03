package com.example.newsdispatcher.screen.favouritescreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsdispatcher.database.NewsDispatcherDatabase
import com.example.newsdispatcher.database.dao.SavedEntryDao
import com.example.newsdispatcher.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(private val dao: SavedEntryDao) : ViewModel() {
    private val _dataset = MutableStateFlow<List<Article>>(emptyList())
    private val _data = MutableStateFlow<List<Article>>(emptyList())
    val data = _data.asStateFlow()
    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            dao.getAllFlow().collect { dataset ->
                _dataset.emit(dataset)
                performSearch()
            }
        }
    }

    fun delete(savedArticle: Article) {
        viewModelScope.launch {
            dao.delete(savedArticle)
        }
    }

    fun updateQuery(newQuery: String) {
        viewModelScope.launch {
            _searchQuery.emit(newQuery)
        }
    }

    fun performSearch() {
        viewModelScope.launch(Dispatchers.IO) {
            _data.emit(_dataset.value.filter {
                it.title?.contains(_searchQuery.value, true) == true || it.content?.contains(
                    _searchQuery.value,
                    true
                ) == true
            })
        }
    }

    companion object {
        fun provideFactory(context: Context) = viewModelFactory {
            initializer {
                val dao = NewsDispatcherDatabase.getInstance(context).getSavedEntryDao()
                FavouriteViewModel(dao)
            }
        }
    }
}