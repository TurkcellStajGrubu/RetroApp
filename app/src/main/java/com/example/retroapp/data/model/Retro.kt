package com.example.retroapp.data.model

import com.google.firebase.Timestamp

data class Retro(
    val id: String = "",
    val admin: String = "",
    val notes: List<Notes> = emptyList(),
    val isActive: Boolean = false,
    val title: String = "",
    val time: Int = 0,
    val endTime: Timestamp? = null
)