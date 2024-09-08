package com.example.newsdispatcher.screen.newsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.newsdispatcher.api.NewsServiceImp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(service: NewsServiceImp) : ViewModel() {

    private val _currentCategory = MutableStateFlow<String?>(null)
    val currentCategory = _currentCategory.asStateFlow()

    init {
        service
    }

    fun setCategory(newCategory: String?) {
        viewModelScope.launch {
            _currentCategory.emit(newCategory)
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