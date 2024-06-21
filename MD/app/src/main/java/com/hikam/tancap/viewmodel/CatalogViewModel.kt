package com.hikam.tancap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hikam.tancap.data.api.ApiConfig
import com.hikam.tancap.data.response.Catalog
import kotlinx.coroutines.launch

class CatalogViewModel : ViewModel() {

    private val _catalogs = MutableLiveData<List<Catalog>>()
    val catalogs: LiveData<List<Catalog>> = _catalogs

    init {
        fetchCatalogs()
    }

    private fun fetchCatalogs() {
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getCatalog()
                _catalogs.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
