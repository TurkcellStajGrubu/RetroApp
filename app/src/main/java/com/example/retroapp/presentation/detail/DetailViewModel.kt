package com.example.retroapp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retroapp.data.StorageRepository
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
    private val hasUser: Boolean
        get() = storageRepository.hasUser()

    private val user: FirebaseUser?
        get() = storageRepository.user()


    fun addNote(
        title: String,
        description: String,
        timestamp: Timestamp,
        type: String,
        onComplete: (Boolean) -> Unit
    ) {
        val username = user?.displayName ?: ""
        viewModelScope.launch {
            storageRepository.addNote(
                userId = "0",
                username = username,
                title = title,
                description = description,
                timestamp = timestamp,
                type = type,
                onComplete = onComplete
            )
        }
    }
}