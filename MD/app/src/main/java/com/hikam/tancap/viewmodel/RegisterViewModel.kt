package com.hikam.tancap.viewmodel

import androidx.lifecycle.ViewModel
import com.hikam.tancap.data.UserDataRepository


class RegisterViewModel(private val repository: UserDataRepository): ViewModel() {
    fun register(name: String, email: String, password: String) =
        repository.signup(name,email, password)
}