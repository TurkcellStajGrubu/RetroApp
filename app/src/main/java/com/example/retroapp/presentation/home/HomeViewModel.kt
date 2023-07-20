package com.example.retroapp.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retroapp.data.Resource
import com.example.retroapp.data.StorageRepository
import com.example.retroapp.data.model.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storageRepository: StorageRepository
) : ViewModel() {
    private val _notesState = MutableStateFlow<Resource<List<Notes>>>(Resource.Loading)
    val notesState: StateFlow<Resource<List<Notes>>> = _notesState

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            storageRepository.getNotes().collect { resource ->
                _notesState.value = resource
            }
        }
    }
}
