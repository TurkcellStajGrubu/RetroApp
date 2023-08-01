package com.example.retroapp.presentation.retro


import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
class RetroViewModel @Inject constructor (
    private val authRepository: AuthRepository,
    private val storageRepository: StorageRepository
) : ViewModel() {
    private val _activeStatus = MutableStateFlow(false)
    val activeStatus: StateFlow<Boolean> = _activeStatus.asStateFlow()
    private val _activeRetroIdState = MutableStateFlow("")
    val activeRetroIdState: StateFlow<String> = _activeRetroIdState.asStateFlow()
    var retro by mutableStateOf(Retro())

    init {
        getActiveRetroId()
        refreshActiveStatus()
    }

    private var countDownTimer: CountDownTimer? = null

    var remainingTimeInSeconds by mutableStateOf(0L)
        private set

    private val hasUser: Boolean
        get() = storageRepository.hasUser()

    private val user: FirebaseUser?
        get() = storageRepository.user()

    fun refreshActiveStatus() {
        viewModelScope.launch {
            storageRepository.isActive().collect { newStatus ->
                _activeStatus.value = newStatus
            }
        }
    }

    fun getMeetingOwnerName(): String {
        val currentUser = authRepository.currentUser
        return currentUser?.displayName ?: "Kullanıcı Adı Yok"
    }

    fun startCountDownTimer(saat: Int, dakika: Int) {
        val toplamSaniye = (saat * 3600 + dakika * 60).toLong()
        countDownTimer = object : CountDownTimer(toplamSaniye * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTimeInSeconds = millisUntilFinished / 1000
                Log.d("CustomDialog", "Kalan Süre: $remainingTimeInSeconds saniye")
            }

            override fun onFinish() {
                // Geri sayım tamamlandığında yapılacak işlemler
            }
        }.start()
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

    fun getRetro(retroId: String) {
        viewModelScope.launch {
            storageRepository.getRetro(retroId, onError = {},) {
                if (it != null) {
                    retro = it
                } else {
                    Log.d("null", "null")
                }
            }
        }
    }
    fun getActiveRetroId() {
        viewModelScope.launch {
            // Flow'ı collect kullanarak başlatın ve işlem sonlandığında akışı durdurun
            storageRepository.getActiveRetroId().collect { id ->
                // Aktif retro ID'sini MutableState'e atayın
                _activeRetroIdState.value = id
            }
        }
    }

    fun addNotesToRetro(retroId: String, notes: Notes){
        viewModelScope.launch {
            storageRepository.addNotesToRetro(retroId, notes)
        }
    }
}