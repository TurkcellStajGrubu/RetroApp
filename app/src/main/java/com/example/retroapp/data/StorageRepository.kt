package com.example.retroapp.data


import com.example.retroapp.data.model.Notes
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow


interface StorageRepository {

suspend fun addNote(
    userId: String,
    username:String,
    title: String,
    description: String,
    timestamp: Timestamp,
    type: String,
    onComplete: (Boolean) -> Unit)

    suspend fun deleteNote(noteId: String,onComplete: (Boolean) -> Unit)

    suspend fun updateNote(
        title: String,
        note:String,
        noteId: String,
        onResult:(Boolean) -> Unit
    )

    suspend fun getNotesByType(
        type: String,
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
}