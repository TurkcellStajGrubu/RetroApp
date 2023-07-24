package com.example.retroapp.data

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.retroapp.data.model.Notes
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


const val NOTES_COLLECTION_REF = "notes"

class StorageRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage
) : StorageRepository {

    private val notesCollection: CollectionReference = firebaseFirestore.collection("notes")
    override fun user() = auth.currentUser
    override fun hasUser(): Boolean = auth.currentUser != null
    override fun getUserId(): String = auth.currentUser?.uid.orEmpty()

    private val notesRef: CollectionReference = firebaseFirestore.collection(NOTES_COLLECTION_REF)

    override suspend fun getNotesByType(
        type: String,
    ): Flow<Resource<List<Notes>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            snapshotStateListener = notesRef
                .orderBy("timestamp")
                .whereEqualTo("type", type)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val notes = snapshot.toObjects(Notes::class.java)
                        Resource.Success(result = notes)
                    } else {
                        e?.let { Resource.Failure(exception = it) }
                    }
                    response?.let { trySend(it) }
                }
        } catch (e: Exception) {
            trySend(Resource.Failure(e))
            e.printStackTrace()
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    override fun getNotes(): Flow<Resource<List<Notes>>> = callbackFlow {
        val listenerRegistration: ListenerRegistration = notesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Resource.Failure(error))
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val notesList: List<Notes> = snapshot.toObjects(Notes::class.java)
                trySend(Resource.Success(notesList))
            }
        }

        awaitClose {
            listenerRegistration.remove()
        }
    }

  override suspend fun getNoteById(
        noteId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Notes?) -> Unit
    ) {
        notesRef
            .document(noteId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Notes::class.java))
            }
            .addOnFailureListener {result ->
                onError.invoke(result.cause)
            }
      firebaseFirestore
    }

    override suspend fun addNote(
        userId: String,
        username: String,
        title: String,
        description: String,
        images: List<Uri>,
        timestamp: Timestamp,
        type: String,
        onComplete: (Boolean) -> Unit
    ) {
        val id = notesRef.document().id
        val list = mutableListOf<String>()

        val deferreds = images.map { uri ->
            CoroutineScope(Dispatchers.IO).async {
                val uid = uri.toString()
                val taskSnapshot = firebaseStorage.reference.child(uid).putFile(uri).await()
                val url = taskSnapshot.metadata?.reference?.downloadUrl?.await()
                url?.let { list.add(it.toString()) }
            }
        }
        deferreds.awaitAll()
        val note = Notes(
            id,
            userId,
            list,
            username,
            title,
            description,
            timestamp,
            type
        )
        notesRef
            .document(id)
            .set(note)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

   override suspend fun deleteNote(noteId: String,onComplete: (Boolean) -> Unit){
        notesRef.document(noteId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    override fun getFilteredNotes(
        searchText: String,
        filterType: String
    ): Flow<Resource<List<Notes>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null
        try {
            var query = notesRef.orderBy("timestamp")
            if (searchText.isNotEmpty()) {
                query = query.whereGreaterThanOrEqualTo("title", searchText)
            }
            if (filterType.isNotEmpty()) {
                query = query.whereEqualTo("type", filterType)
            }

            snapshotStateListener = query.addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val notes = snapshot.toObjects(Notes::class.java)
                    Resource.Success(result = notes)
                } else {
                    e?.let { Resource.Failure(exception = it) }
                }
                response?.let { trySend(it) }
            }
        } catch (e: Exception) {
            trySend(Resource.Failure(e))
            e.printStackTrace()
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    override suspend fun updateNote(
        title: String,
        note: String,
        noteId: String,
        images: List<Uri>,
        type: String,
        userId: String,
        username: String,
        onResult:(Boolean) -> Unit
    ){
        val list = mutableListOf<String>()
        val deferreds = images.map { uri ->
            CoroutineScope(Dispatchers.IO).async {
                val uid = uri.toString()
                val taskSnapshot = firebaseStorage.reference.child(uid).putFile(uri).await()
                val url = taskSnapshot.metadata?.reference?.downloadUrl?.await()
                url?.let { list.add(it.toString()) }
            }
        }
        deferreds.awaitAll()
        val updateData = hashMapOf<String,Any>(
            "userId" to userId,
            "username" to username,
            "timestamp" to Timestamp.now(),
            "description" to note,
            "title" to title,
            "type" to type,
            "images" to list
        )
        notesRef.document(noteId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }

    fun signOut() = auth.signOut()
}