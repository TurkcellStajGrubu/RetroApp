package com.example.retroapp.data.model

data class Retro(
    val id: String = "",
    val admin: String = "",
    val users: List<String> = listOf(),
    val notes: List<Notes> = listOf(),
    val isActive: Boolean = false,
    val isPrepare: Boolean = false,
    val time: Int = 0
)