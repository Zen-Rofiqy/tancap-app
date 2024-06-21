package com.hikam.tancap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hikam.tancap.data.ResultState
import com.hikam.tancap.data.UserDataRepository
import com.hikam.tancap.data.response.RegisterResponse
import com.hikam.tancap.userpref.UserModel
import kotlinx.coroutines.flow.Flow

class RegisterViewModel(private val userDataRepository: UserDataRepository) : ViewModel() {

    fun register(username: String, email: String, password: String): LiveData<ResultState<RegisterResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = userDataRepository.register(username, email, password)
            emitSource(response)
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "An unknown error occurred"))
        }
    }
}
