package com.hikam.tancap.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.hikam.tancap.data.api.ApiService
import com.hikam.tancap.data.response.ErrorResponse
import com.hikam.tancap.data.response.LoginResponse
import com.hikam.tancap.data.response.RegisterResponse
import com.hikam.tancap.userpref.UserModel
import com.hikam.tancap.userpref.UserPreferencesManager
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserDataRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferencesManager
) {

    fun login(email: String, password: String): LiveData<ResultState<LoginResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.login(email, password)
            saveSession(UserModel(response.token, email, password, true))
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    fun register(username: String, email: String, password: String): LiveData<ResultState<RegisterResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.register(username, email, password)
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreferences.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    suspend fun logout() {
        userPreferences.logout()
    }

    companion object {
        private var INSTANCE: UserDataRepository? = null

        fun clearInstance() {
            INSTANCE = null
        }

        fun getInstance(apiService: ApiService, userPreferences: UserPreferencesManager): UserDataRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserDataRepository(apiService, userPreferences)
            }.also { INSTANCE = it }
    }
}

