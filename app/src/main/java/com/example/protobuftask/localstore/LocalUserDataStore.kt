package com.example.protobuftask.localstore

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.protobuftask.model.AddressProto
import com.example.protobuftask.model.CompanyProto
import com.example.protobuftask.model.GeoProto
import com.example.protobuftask.model.UserItemsProto
import com.example.protobuftask.model.UserResponseItem
import com.example.protobuftask.model.UsersProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException

class LocalUserDataStore(private val context: Context) {

    private val Context.userStore: DataStore<UserItemsProto> by dataStore(
        fileName = USER_DATA_STORE_FILE_NAME,
        serializer = UserProtoSerializer,
    )

    private val accountFlow: Flow<UserItemsProto> =
        context.userStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e("SARTHAK", "Error reading sort order preferences.")
                    emit(UserItemsProto.getDefaultInstance())
                } else {
                    throw exception
                }
            }

    suspend fun getUsersAccount(): UserItemsProto = accountFlow.first()

    suspend fun updateUserAccount(userResponseList: List<UserResponseItem>) {
        context.userStore.updateData { preferences ->
            preferences.toBuilder()
                .clearUsers()
                .addAllUsers(
                    userResponseList.map { item ->
                        UsersProto
                            .newBuilder()
                            .setId(item.id.toLong())
                            .setName(item.name)
                            .setUsername(item.username)
                            .setEmail(item.email)
                            .setAddress(
                                AddressProto
                                    .newBuilder()
                                    .setCity(item.address.city)
                                    .setStreet(item.address.street)
                                    .setSuite(item.address.suite)
                                    .setZipcode(item.address.zipcode)
                                    .setGeo(
                                        GeoProto.newBuilder()
                                            .setLat(item.address.geo.lat)
                                            .setLng(item.address.geo.lng)
                                            .build()
                                    ).build()
                            )
                            .setCompany(
                                CompanyProto.newBuilder()
                                    .setName(item.company.name)
                                    .setCatchPhrase(item.company.catchPhrase)
                                    .setBs(item.company.bs)
                                    .build()
                            )
                            .setPhone(item.phone)
                            .setWebsite(item.website)
                            .build()
                    }
                ).build()
        }
    }


    companion object {
        private const val USER_DATA_STORE_FILE_NAME = "user_proto.pb"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: LocalUserDataStore? = null

        fun getInstance(context: Context): LocalUserDataStore {
            return instance ?: synchronized(this) {
                instance ?: LocalUserDataStore(context).also { instance = it }
            }
        }
    }
}