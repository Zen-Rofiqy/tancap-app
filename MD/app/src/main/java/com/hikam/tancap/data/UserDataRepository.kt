package com.hikam.tancap.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.hikam.tancap.data.api.ApiService
import com.hikam.tancap.data.response.ErrorResponse
import com.hikam.tancap.data.response.RegisterResponse
import com.hikam.tancap.userpref.UserModel
import com.hikam.tancap.userpref.UserPreferencesManager
import com.google.gson.Gson
import com.hikam.tancap.data.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File


class UserDataRepository(
    private val apiService: ApiService,
    private val userPreferences: UserPreferencesManager,
){

    fun login(email: String,password: String): LiveData<ResultState<LoginResponse>> = liveData{
        emit(ResultState.Loading)
        try {
            val response = apiService.login(email,password)
            emit(ResultState.Success(response))
        }catch (e:HttpException){
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        }
    }

    fun signup(name: String, email: String, password: String): LiveData<ResultState<RegisterResponse>> = liveData{
        emit(ResultState.Loading)
        try {
            val response = apiService.register(name,email,password)
            emit(ResultState.Success(response))
        }catch (e: HttpException){
            val error = e.response()?.errorBody()?.string()
            val body = Gson().fromJson(error, ErrorResponse::class.java)
            emit(ResultState.Error(body.message))
        }
    }

    suspend fun saveSession(user: UserModel){
        userPreferences.saveSession(user)
    }
    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }
    suspend fun logout(){
        userPreferences.logout()
    }

    companion object{
        private var INSTANCE: UserDataRepository? = null

        fun clearInstance(){
            INSTANCE = null
        }

        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferencesManager
        ): UserDataRepository =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserDataRepository(apiService,userPreferences)
            }.also { INSTANCE = it }
    }
}