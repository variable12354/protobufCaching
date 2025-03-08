package com.example.protobuftask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.protobuftask.model.UserResponseItem
import com.example.protobuftask.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UsersProfileViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _user = MutableStateFlow<List<UserResponseItem>>(emptyList())
    val user = _user.asStateFlow()

    init {
        fetchUsers()
    }

    private fun fetchUsers(refresh: Boolean = false) = viewModelScope.launch {
        repository.getUsers(refresh).collectLatest {
            _user.value = it
        }
    }

}