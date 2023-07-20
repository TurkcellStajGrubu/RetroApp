package com.example.retroapp.data

import android.net.Uri
import com.example.retroapp.data.model.Notes
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


const val NOTES_COLLECTION_REF = "notes"

class StorageRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val auth: FirebaseAuth,

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
    ){
        notesRef
            .document(noteId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Notes::class.java))
            }
            .addOnFailureListener {result ->
                onError.invoke(result.cause)
            }


    }

    override suspend fun addNote(
        userId: String,
        username: String,
        title: String,
        description: String,
        images: List<String>,
        timestamp: Timestamp,
        type: String,
        onComplete: (Boolean) -> Unit
    ) {
        val id = notesRef.document().id
        val note = Notes(
            id,
            userId,
            images,
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

    override suspend fun updateNote(
        title: String,
        note: String,
        noteId: String,
        images: List<String>,
        onResult:(Boolean) -> Unit
    ){
        val updateData = hashMapOf<String,Any>(
            "timestamp" to Timestamp.now(),
            "description" to note,
            "title" to title,
        )

        notesRef.document(noteId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }

    fun signOut() = auth.signOut()
}

/*sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)

}*/