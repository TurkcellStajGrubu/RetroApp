package com.example.retroapp.presentation.retro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AlertDialogViewModel:ViewModel() {
    var isDialogShown by mutableStateOf(false)
        private set

    fun onPurchaseClick(){
        isDialogShown = true
    }

    fun onDismissDialog(){
        isDialogShown = false
    }
}