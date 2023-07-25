package com.example.retroapp.data

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    suspend fun signup(name: String, email: String, password: String): Resource<FirebaseUser>
    suspend fun saveUsernameToDatabase(username: String, email: String): Resource<Boolean>
    fun logout()
    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }
}