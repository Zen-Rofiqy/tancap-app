package com.hikam.tancap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hikam.tancap.data.api.ApiConfig
import com.hikam.tancap.data.api.ApiConfigNews
import com.hikam.tancap.data.response.Article
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> = _news

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val response = ApiConfigNews.getApiService().getNews()
                if (response.status == "ok") {
                    _news.value = response.articles
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
