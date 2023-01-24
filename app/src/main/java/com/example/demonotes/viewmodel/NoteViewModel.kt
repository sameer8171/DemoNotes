package com.example.demonotes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demonotes.model.NoteRequest
import com.example.demonotes.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) :ViewModel() {
    val noteLiveData get() = noteRepository.noteLiveData
    val statusLiveData get() = noteRepository.statusLiveData

    fun getNote(){
        viewModelScope.launch {
             noteRepository.getNote()
        }
    }

    fun deleteNote(noteId:String){
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }
    fun createNote(noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepository.createNote(noteRequest)
        }
    }
    fun updateNote(noteId: String,noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepository.updateNote(noteId,noteRequest)
        }
    }

}