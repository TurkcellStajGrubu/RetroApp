package com.example.retroapp.presentation.retro.chat

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retroapp.data.StorageRepository
import com.example.retroapp.data.model.Notes
import com.example.retroapp.data.model.Retro
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val storageRepository: StorageRepository) : ViewModel() {
    val activeRetro: MutableState<Retro?> = mutableStateOf(null)
    val adminName: MutableState<String?> = mutableStateOf(null)
    val meetingTitle: MutableState<String?> = mutableStateOf(null)
    val remainingTime: MutableState<String> = mutableStateOf("")
    private var timer: CountDownTimer? = null
    val meetingAdminId: MutableState<String?> = mutableStateOf(null)
    val activeRetroId:MutableState<String> = mutableStateOf("")
    private var timerJob: Job? = null
    var retro by mutableStateOf(Retro())

    val resetEvent = MutableStateFlow(false)
    private val user: FirebaseUser?
        get() = storageRepository.user()

    init {
        viewModelScope.launch {
            storageRepository.isActive().collect { isActive ->
                if (!isActive) {
                    resetEvent.value = true
                }
                if (isActive) {
                    activeRetroId.value = storageRepository.getActiveRetroId().first()
                    storageRepository.getRetro(activeRetroId.value, onError = {
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
                        retro?.endTime?.let { endTime ->
                            startTimer(endTime)
                        }
                    }
                } else {
                    activeRetro.value = null
                    adminName.value = null
                    meetingTitle.value = null
                }
            }
        }
    }

    fun calculateRemainingTime(endTime: Timestamp): String {
        val remainingSeconds = endTime.seconds - Timestamp.now().seconds
        val minutes = remainingSeconds / 60
        val seconds = remainingSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
    fun startTimer(endTime: Timestamp) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                val remainingSeconds = endTime.seconds - Timestamp.now().seconds
                if (remainingSeconds <= 0) {
                    remainingTime.value = "00:00"
                    break
                }
                val remainingTimeStr = calculateRemainingTime(endTime)
                remainingTime.value = remainingTimeStr
                delay(1000) // 1 saniye bekleyin
            }
        }
    }

    fun updateRetroTime(newTime: Int) {
        activeRetro.value?.let { retro ->
            viewModelScope.launch {
                storageRepository.updateRetroTime(retro.id, newTime) { isSuccess ->
                    if (isSuccess) {
                        val newEndTime = Timestamp.now().seconds + newTime * 60
                        startTimer(Timestamp(newEndTime, 0))
                    } else {
                        // handle failure
                    }
                }
            }
        }
    }

    fun addNotesToRetro(retroId: String, notes: Notes){
        notes.username = user?.displayName ?: ""
        viewModelScope.launch {
            storageRepository.addNotesToRetro(retroId, notes)
        }
    }

    fun deleteNotesFromRetro(retroId: String, notes: Notes){
        viewModelScope.launch {
            storageRepository.deleteNotesFromRetro(retroId, notes)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
    val getUserId: String
        get() = storageRepository.getUserId()

    fun getRetro(retroId: String):Retro {
        viewModelScope.launch {
            storageRepository.getRetro(retroId, onError = {},) {
                if (it != null) {
                    retro = it
                } else {
                    Log.d("null", "null")
                }
            }
        }
        return retro
    }

    fun addConfirmedNotes(retroId: String){
        viewModelScope.launch {
            storageRepository.addConfirmedNotes(retroId)
        }
    }


}