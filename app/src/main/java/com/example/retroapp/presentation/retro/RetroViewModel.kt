package com.example.retroapp.presentation.retro


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retroapp.data.AuthRepository
import com.example.retroapp.data.StorageRepository
import com.example.retroapp.data.model.Notes
import com.example.retroapp.data.model.Retro
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetroViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val storageRepository: StorageRepository
) : ViewModel() {
    val _activeStatus = MutableStateFlow(false)
    val activeStatus: StateFlow<Boolean> = _activeStatus
    private val _activeRetroIdState = MutableStateFlow("")
    val activeRetroIdState: StateFlow<String> = _activeRetroIdState.asStateFlow()
    var retro by mutableStateOf(Retro())

    init {
        getActiveRetroId()
        refreshActiveStatus()
    }


    private val user: FirebaseUser?
        get() = storageRepository.user()

    fun refreshActiveStatus() {
        viewModelScope.launch {
            storageRepository.isActive()
                .collect { newStatus ->
                    _activeStatus.value = newStatus
                }
        }
    }


    fun createRetro(
        notes: List<Notes>,
        isActive: Boolean,
        title: String,
        time: Int,
        onComplete: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            storageRepository.createRetro(
                admin = user!!.uid,
                notes,
                isActive,
                title,
                time,
                onComplete
            )
        }
    }

    fun getActiveRetroId() {
        viewModelScope.launch {
            // Flow'ı collect kullanarak başlattık ve işlem sonlandığında akışı durdurduk
            storageRepository.getActiveRetroId().collect { id ->
                // Aktif retro ID'sini MutableState'e atama
                _activeRetroIdState.value = id
            }
        }
    }

}