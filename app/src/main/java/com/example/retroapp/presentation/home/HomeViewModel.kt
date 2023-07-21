package com.example.retroapp.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retroapp.data.Resource
import com.example.retroapp.data.StorageRepository
import com.example.retroapp.data.model.Notes
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _notesState = MutableStateFlow<Resource<List<Notes>>>(Resource.Loading)
    val notesState: StateFlow<Resource<List<Notes>>> = _notesState

    init {
        fetchNotes()
    }

    fun logout() {
        auth.signOut()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            storageRepository.getNotes().collect { resource ->
                _notesState.value = resource
            }
        }
    }

    fun getFilteredNotes(searchText: String, filterType: String): Flow<Resource<List<Notes>>> {
        val initialNotesState = notesState.value

        if (initialNotesState == null) {
            return notesState // Tüm notları döndür
        }

        return notesState.map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading
                is Resource.Success -> {
                    val filteredNotes = if (filterType.isEmpty()) {
                        // Filtreleme tipi seçilmemişse, sadece arama yap
                        resource.result.filter { note ->
                            note.username.contains(searchText, ignoreCase = true) ||
                                    note.title.contains(searchText, ignoreCase = true) ||
                                    note.description.contains(searchText, ignoreCase = true) ||
                                    note.type.contains(searchText, ignoreCase = true)
                        }
                    } else {
                        // Filtreleme tipi seçilmişse, tip ve aramayı birleştirerek filtrele
                        resource.result.filter { note ->
                            note.type.equals(filterType, ignoreCase = true) &&
                                    (note.username.contains(searchText, ignoreCase = true) ||
                                            note.title.contains(searchText, ignoreCase = true) ||
                                            note.description.contains(searchText, ignoreCase = true))
                        }
                    }
                    Resource.Success(filteredNotes)
                }
                is Resource.Failure -> Resource.Failure(resource.exception)
            }
        }
    }


}
