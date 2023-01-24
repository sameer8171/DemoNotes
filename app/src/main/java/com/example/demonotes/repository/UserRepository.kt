package com.example.demonotes.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demonotes.api.UserApi
import com.example.demonotes.model.UserRequest
import com.example.demonotes.model.UserResponce
import com.example.demonotes.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponce>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponce>>
        get() = _userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.signUp(userRequest)
        handleResponse(response)

    }

    suspend fun loginUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userApi.signIn(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponce>) {

        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("SomeThing went wrong"))
        }
    }


}