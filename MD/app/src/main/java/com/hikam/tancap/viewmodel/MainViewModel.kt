package com.hikam.tancap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hikam.tancap.data.UserDataRepository
import com.hikam.tancap.userpref.UserModel

class MainViewModel(private val repository: UserDataRepository): ViewModel() {

    suspend fun logout(){
        repository.logout()
    }
    fun getSession(): LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }
}