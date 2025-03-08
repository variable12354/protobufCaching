package com.example.protobuftask.repository

import android.util.Log
import com.example.protobuftask.Controller
import com.example.protobuftask.localstore.LocalUserDataStore
import com.example.protobuftask.model.UserResponseItem
import com.example.protobuftask.model.toDomain
import com.example.protobuftask.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository {
    private val apiService = RetrofitClient.apiService
    val localDataSource = LocalUserDataStore.getInstance(Controller.instance)

    suspend fun getUsers(refresh: Boolean): Flow<List<UserResponseItem>> = flow {
        if (refresh) {
            val result = apiService.getUser()
            if (result.isSuccessful) {
                emit(result.body() ?: emptyList())
                result.body()?.let {
                    localDataSource.updateUserAccount(it)
                }
                Log.e("Network", "Success:${result.body()}")
            }
            Log.e("Network", "getUsers:${result.body()}")
        } else {
            val usersCatch = localDataSource.getUsersAccount().usersList.map {
                it.toDomain()
            }
            Log.e("Catched", "getUsers:$usersCatch")
            emit(usersCatch)
        }
    }.flowOn(Dispatchers.IO)
}