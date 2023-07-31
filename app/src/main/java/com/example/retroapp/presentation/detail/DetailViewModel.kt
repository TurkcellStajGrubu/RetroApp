package com.example.retroapp.presentation.detail

import android.net.Uri
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
    var note by mutableStateOf(Notes())
    var listUri by mutableStateOf<List<Uri>>(emptyList())

    private val hasUser: Boolean
        get() = storageRepository.hasUser()

    private val user: FirebaseUser?
        get() = storageRepository.user()

    fun addNote(
        title: String,
        description: String,
        images: List<Uri>,
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

    fun getNote(noteId:String) {
        viewModelScope.launch {
            storageRepository.getNoteById(
                noteId = noteId,
                onError = {},
            ){
                if (it != null) {
                    note = it
                } else{
                    Log.d("null", "null")
                }
                val list = arrayListOf<Uri>()
                note.images.forEach {
                    list.add(Uri.parse(it))
                }
                listUri = list
            }
        }
    }

    fun updateNote(
        title: String,
        note: String,
        noteId: String,
        images: List<Uri>,
        type: String,
        onResult:(Boolean) -> Unit
    ){
        val username: String = user?.displayName ?: ""
        viewModelScope.launch {
            storageRepository.updateNote(title, note, noteId, images, type, user!!.uid, username, onResult)
        }
    }
    fun onTitleChange(title: String) {
        note = note.copy(title = title)
    }

    fun onTypeChange(type: String) {
        note = note.copy(type = type)
    }

    fun onDetailChange(detail: String) {
        note = note.copy(description = detail)
    }

    fun onImagesChange(images: List<Uri>) {
        val list = arrayListOf<String>()
        images.forEach {
            list.add(it.toString())
        }
        note = note.copy(images = list)
    }

    fun deleteImage(noteId: String, imageUri: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            storageRepository.deleteImage(noteId, imageUri, onComplete)
        }
    }
}