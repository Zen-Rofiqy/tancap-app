package com.hikam.tancap.viewmodel

import androidx.lifecycle.ViewModel
import com.hikam.tancap.data.UserDataRepository
import com.hikam.tancap.userpref.UserModel

class LoginViewModel(private val repository: UserDataRepository): ViewModel() {

    fun login(email: String, password: String) = repository.login(email,password)

    suspend fun saveSession(userModel: UserModel){
        repository.saveSession(userModel)
    }
}