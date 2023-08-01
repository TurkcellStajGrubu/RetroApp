package com.example.retroapp.data.model

import android.net.Uri
import com.google.firebase.Timestamp

data class Notes(
    var id: String = "",
    val userId: String = "",
    val images: List<String> = listOf<String>(),
    var username: String = "",
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val type: String = "",
)
