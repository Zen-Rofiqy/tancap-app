package com.hikam.tancap.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hikam.tancap.data.UserDataRepository
import com.hikam.tancap.data.di.Injection

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: UserDataRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel() as T
            }
            modelClass.isAssignableFrom(CatalogViewModel::class.java) -> {
                CatalogViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun clearInstance() {
            UserDataRepository.clearInstance()
            INSTANCE = null
        }

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val instance = ViewModelFactory(Injection.provideRepository(context))
                INSTANCE = instance
                instance
            }
        }
    }
}