package com.example.retroapp.data.model

import android.net.Uri
import com.google.firebase.Timestamp

data class Notes(
    val id: String = "",
    val userId: String = "",
    val images: List<String>? = null,
    val username: String = "",
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val type: String = "",
)
