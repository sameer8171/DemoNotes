package com.example.demonotes.viewmodel

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demonotes.model.UserRequest
import com.example.demonotes.model.UserResponce
import com.example.demonotes.repository.UserRepository
import com.example.demonotes.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {
    val userResponseLiveData: LiveData<NetworkResult<UserResponce>>
        get() = repository.userResponseLiveData

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            repository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            repository.loginUser(userRequest)
        }
    }

    fun validateCredentials(
        username: String,
        email: String,
        password: String,
        isLogin:Boolean
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (!isLogin && TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            result = Pair(false, "Please provide the credentials")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            result = Pair(false, "Please provide the valid email")
        } else if (password.length <= 5) {
            result = Pair(false, "password length should be greater then 5")
        }


        return result
    }

}