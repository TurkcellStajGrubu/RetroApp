package com.example.retroapp.presentation.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retroapp.data.StorageRepository
import com.example.retroapp.data.model.Notes
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val storageRepository: StorageRepository
) : ViewModel()
{
    private var note by mutableStateOf(Notes())

    private val hasUser: Boolean
        get() = storageRepository.hasUser()

    private val user: FirebaseUser?
        get() = storageRepository.user()

    fun addNote(
        title: String,
        description: String,
        images: List<String>,
        timestamp: Timestamp,
        type: String,
        onComplete: (Boolean) -> Unit
    )
    {
        val username: String = user?.displayName ?: ""
        viewModelScope.launch {
            if (hasUser){
                storageRepository.addNote(userId = user!!.uid, username, title, description, images, timestamp, type, onComplete)
            }

        }
    }

    fun getNote(noteId:String) : Notes{
        viewModelScope.launch {
            storageRepository.getNoteById(
                noteId = noteId,
                onError = {},
            ){
                if (it != null) {
                    Log.d("it", it.toString())
                    note = it
                } else{
                    Log.d("null", "null")
                }
            }
        }
        return note
    }

    fun updateNote(
        title: String,
        note: String,
        noteId: String,
        images: List<String>,
        type: String,
        onResult:(Boolean) -> Unit
    ){
        viewModelScope.launch {
            storageRepository.updateNote(title, note, noteId, images, type, onResult)
        }
    }
}