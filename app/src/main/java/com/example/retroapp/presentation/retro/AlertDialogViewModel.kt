package com.example.retroapp.presentation.retro

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.retroapp.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlertDialogViewModel @Inject constructor (private val authRepository: AuthRepository) : ViewModel() {
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

}