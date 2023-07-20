package com.example.retroapp.data.model

import com.google.firebase.Timestamp

data class Notes(
    val Id: String = "",
    val userId: String = "",
    val username:String="",
    //val images: List<Uri>? = null,
    val title: String = "",
    val description: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val type: String = "",
)