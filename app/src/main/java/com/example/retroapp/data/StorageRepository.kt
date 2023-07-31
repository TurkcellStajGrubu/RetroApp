package com.example.retroapp.data


import android.net.Uri
import com.example.retroapp.data.model.Notes
import com.example.retroapp.data.model.Retro
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface StorageRepository {

    suspend fun addNote(
        userId: String,
        username:String,
        title: String,
        description: String,
        images: List<Uri>,
        timestamp: Timestamp,
        type: String,
        onComplete: (Boolean) -> Unit)

    suspend fun deleteNote(noteId: String,onComplete: (Boolean) -> Unit)

    suspend fun updateNote(
        title: String,
        note:String,
        noteId: String,
        images: List<Uri>,
        type: String,
        userId: String,
        username: String,
        onResult:(Boolean) -> Unit
    )

    fun getFilteredNotes(
        searchText: String,
        filterType: String
    ): Flow<Resource<List<Notes>>>


    suspend fun getNoteById(
        noteId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Notes?) -> Unit
    )

    fun user(): FirebaseUser?

    fun hasUser(): Boolean

    fun getUserId(): String

    fun getNotes(): Flow<Resource<List<Notes>>>

    suspend fun getRetro(
        retroId: String,
        onError:(Throwable?) -> Unit,
        onSuccess: (Retro?) -> Unit
    )

    suspend fun createRetro(
        admin: String,
        notes: List<Notes>,
        isActive: Boolean,
        title: String,
        time: Int,
        onComplete: (Boolean) -> Unit
    )

    suspend fun isActive(): Flow<Boolean>
    suspend fun getActiveRetroId(): Flow<String>
    suspend fun addNotesToRetro(retroId: String, notes: Notes)
    suspend fun getUserNameById(userId: String): String?
    suspend fun deleteNotesFromRetro(retroId: String, notes: Notes)
    suspend fun updateRetroTime(retroId: String, newTime: Int, onComplete: (Boolean) -> Unit)
    suspend fun addConfirmedNotes(retroId: String)

    suspend fun deleteImage(noteId: String, imageUri: String, onComplete: (Boolean) -> Unit)

}