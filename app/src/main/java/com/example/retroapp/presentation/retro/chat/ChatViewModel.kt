package com.example.retroapp.presentation.retro.chat

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retroapp.data.StorageRepository
import com.example.retroapp.data.model.Retro
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val storageRepository: StorageRepository) : ViewModel() {
    val activeRetro: MutableState<Retro?> = mutableStateOf(null)
    val adminName: MutableState<String?> = mutableStateOf(null)
    val meetingTitle: MutableState<String?> = mutableStateOf(null)
    val meetingAdminId: MutableState<String?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            storageRepository.isActive().collect { isActive ->
                if (isActive) {
                    val activeRetroId = storageRepository.getActiveRetroId().first()
                    storageRepository.getActiveRetro(activeRetroId, onError = {
                    }) { retro ->
                        activeRetro.value = retro
                        retro?.admin?.let { adminId ->
                            viewModelScope.launch {
                                val userName = storageRepository.getUserNameById(adminId)
                                adminName.value = userName ?: ""
                                meetingAdminId.value=adminId
                                Log.d("adminName.value", userName ?: "No name found")
                            }
                        }
                        meetingTitle.value = retro?.title
                    }
                } else {
                    activeRetro.value = null
                    adminName.value = null
                    meetingTitle.value = null
                }
            }
        }
    }
    val getUserId: String
        get() = storageRepository.getUserId()
}