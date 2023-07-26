package com.example.retroapp.presentation.retro

import android.os.CountDownTimer
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
import kotlinx.coroutines.flow.Flow
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
    private val _prepareStatus = MutableStateFlow(false)
    val prepareStatus: StateFlow<Boolean> = _prepareStatus.asStateFlow()
    var retro by mutableStateOf(Retro())

    init {
        Log.d("init", "init")
        refreshActiveStatus()
        refreshPrepareStatus()
    }
    private val hasUser: Boolean
        get() = storageRepository.hasUser()

    private val user: FirebaseUser?
        get() = storageRepository.user()

    var isDialogShown by mutableStateOf(false)
        private set

    private var countDownTimer: CountDownTimer? = null
    var remainingTimeInSeconds by mutableStateOf(0L)
        private set

    fun onPurchaseClick() {
        isDialogShown = true
    }

    fun onDismissDialog() {
        isDialogShown = false
    }

    // Kullanıcı adını döndür
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

    // Geri sayım sayacını durdur
    fun stopCountDownTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

   suspend fun isActive() : Flow<Boolean> {
       Log.d("repo", storageRepository.isActive().toString())
       return storageRepository.isActive()
    }

    fun refreshActiveStatus() {
        viewModelScope.launch {
            storageRepository.isActive().collect { newStatus ->
                _activeStatus.value = newStatus
            }
        }
    }

    suspend fun isPrepare() : Flow<Boolean> {
          return storageRepository.isPrepare()
    }

    fun refreshPrepareStatus() {
        viewModelScope.launch {
            storageRepository.isPrepare().collect { newStatus ->
                _prepareStatus.value = newStatus
            }
        }
    }

    fun createRetro(
        users: List<String>,
        notes: List<Notes>,
        isActive: Boolean,
        isPrepare: Boolean,
        time: Int,
        onComplete: (Boolean) -> Unit
    ){
        viewModelScope.launch {
            storageRepository.createRetro(admin = user!!.uid, users, notes, isActive, isPrepare, time, onComplete)
        }
    }

    fun getRetro(retroId: String){
        viewModelScope.launch {
            storageRepository.getActiveRetro(retroId, onError = {},){
                if (it != null) {
                    retro = it
                } else{
                    Log.d("null", "null")
                }
            }
        }
    }

}