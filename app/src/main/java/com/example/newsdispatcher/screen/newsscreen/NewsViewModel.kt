package com.example.newsdispatcher.screen.newsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsdispatcher.api.NewsServiceImp
import com.example.newsdispatcher.model.Article
import com.example.newsdispatcher.utils.NewsScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val service: NewsServiceImp) : ViewModel() {

    private val _currentCategory = MutableStateFlow<String?>(null)
    val currentCategory = _currentCategory.asStateFlow()

    private val _currentDataset = MutableStateFlow<List<Article>>(emptyList())
    val currentDataset = _currentDataset.asStateFlow()

    private val _uiEvent = MutableStateFlow<NewsScreenEvent>(NewsScreenEvent.LOADING)
    val uiEvent = _uiEvent.asStateFlow()

    init {
        viewModelScope.launch {
            val response = service.getTopHeadlines(
                _currentCategory.value,
                10,
                1
            )
            if (response.status == "ok") {
                _currentDataset.emit(response.articles.toList())
            }

        }
    }

    fun setCategory(newCategory: String?) {
        viewModelScope.launch {
            _currentCategory.emit(newCategory)
            val response = service.getTopHeadlines(
                _currentCategory.value,
                10,
                1
            )
            if (response.status == "ok") {
                _currentDataset.emit(response.articles.toList())
            }
        }
    }

    companion object {
        fun provideFactory() = viewModelFactory {
            initializer {
                val service = NewsServiceImp.create()
                NewsViewModel(service)
            }
        }
    }

}