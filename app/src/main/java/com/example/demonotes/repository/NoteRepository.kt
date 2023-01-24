package com.example.demonotes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demonotes.api.NoteApi
import com.example.demonotes.model.NoteRequest
import com.example.demonotes.model.NoteResponse
import com.example.demonotes.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteApi: NoteApi) {
    private val _noteLiveData = MutableLiveData<NetworkResult<List<NoteResponse>>>()
    val noteLiveData: LiveData<NetworkResult<List<NoteResponse>>>
        get() = _noteLiveData

    private val _statusLiveData = MutableLiveData<NetworkResult<String>>()
    val statusLiveData: LiveData<NetworkResult<String>>
        get() = _statusLiveData

    suspend fun getNote() {
        _noteLiveData.postValue(NetworkResult.Loading())
        val response = noteApi.getNotes()
        if (response.isSuccessful && response.body() != null) {
            _noteLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            _noteLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
        } else {
            _noteLiveData.postValue(NetworkResult.Error("SomeThing went wrong"))
        }
    }

    suspend fun createNote(noteRequest: NoteRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteApi.createdNote(noteRequest)
        statusHandler(response, "Note Created")

    }


    suspend fun deleteNote(noteId: String) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteApi.deleteNote(noteId)
        statusHandler(response, "Note Deleted")
    }

    suspend fun updateNote(noteId: String, noteRequest: NoteRequest) {
        _statusLiveData.postValue(NetworkResult.Loading())
        val response = noteApi.updateNote(noteId, noteRequest)
        statusHandler(response, "Note Updated")
    }


    private fun statusHandler(response: Response<NoteResponse>, message: String) {
        if (response.isSuccessful && response.body() != null) {
            _statusLiveData.postValue(NetworkResult.Success(message))
        } else {
            _statusLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}