package com.example.retroapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.retroapp.data.AuthRepository
import com.example.retroapp.data.Resource
import com.example.retroapp.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    private val _isLoggedIn = MutableStateFlow(repository.isUserLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    /*
    val currentUser: FirebaseUser?
        get() = repository.currentUser

     */

    init {
        if (repository.currentUser != null) {
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email, password)
        _loginFlow.value = result
    }

    fun signup(name: String, email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = repository.signup(name, email, password)
        _signupFlow.value = result
    }

    fun logout(navController: NavHostController) = viewModelScope.launch {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
        _isLoggedIn.emit(false)
        navController.navigate(ROUTE_LOGIN)
    }
}