package com.example.retroapp.presentation.home


import androidx.lifecycle.ViewModel
import com.example.retroapp.data.StorageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storageRepository: StorageRepository
) : ViewModel() {
}