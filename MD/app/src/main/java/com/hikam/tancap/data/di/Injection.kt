package com.hikam.tancap.data.di

import android.content.Context
import com.hikam.tancap.data.UserDataRepository
import com.hikam.tancap.data.api.ApiConfig
import com.hikam.tancap.userpref.UserPreferencesManager
import com.hikam.tancap.userpref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserDataRepository {
        val pref = UserPreferencesManager.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return UserDataRepository.getInstance(apiService,pref)
    }
}