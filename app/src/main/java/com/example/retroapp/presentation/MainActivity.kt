package com.example.retroapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.retroapp.navigation.AppNavHost
import com.example.retroapp.navigation.ROUTE_HOME
import com.example.retroapp.navigation.ROUTE_LOGIN
import com.example.retroapp.presentation.auth.AuthViewModel
import com.example.retroapp.presentation.detail.DetailViewModel
import com.example.retroapp.presentation.home.HomeViewModel
import com.example.retroapp.presentation.retro.AlertDialogViewModel
import com.example.retroapp.presentation.retro.chat.ChatViewModel
import com.example.retroapp.presentation.retro.RetroScreen
import com.example.retroapp.presentation.retro.register.RetroRegisterScreen
import com.example.retroapp.presentation.ui.theme.RetroAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel by viewModels<AuthViewModel>()
    private val detailViewModel by viewModels<DetailViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()
    private val alertDialogViewModel by viewModels<AlertDialogViewModel>()
    private val chatViewModel by viewModels<ChatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetroAppTheme {
                val navController = rememberNavController()

                val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

                LaunchedEffect(isLoggedIn) {
                    if (isLoggedIn) {
                        navController.navigate(ROUTE_HOME) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    } else {
                        navController.navigate(ROUTE_LOGIN) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                }

                AppNavHost(
                    authViewModel,
                    homeViewModel,
                    navController = navController,
                    detailViewModel = detailViewModel,
                    alertDialogViewModel = alertDialogViewModel,
                    chatViewModel = chatViewModel
                )
            }
        }
    }
}
