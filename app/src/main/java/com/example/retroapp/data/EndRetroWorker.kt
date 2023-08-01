package com.example.retroapp.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore

class EndRetroWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val retroId = inputData.getString("retroId")
        if (retroId != null) {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection("retro").document(retroId).update("active", false)
        }
        return Result.success()
    }
}