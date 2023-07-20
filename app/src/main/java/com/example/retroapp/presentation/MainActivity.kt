package com.example.retroapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.retroapp.navigation.AppNavHost
import com.example.retroapp.presentation.auth.AuthViewModel
import com.example.retroapp.presentation.detail.DetailViewModel
import com.example.retroapp.presentation.home.HomeViewModel
import com.example.retroapp.presentation.ui.theme.RetroAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel by viewModels<AuthViewModel>()
    private val detailViewModel by viewModels<DetailViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetroAppTheme {
                AppNavHost(authViewModel, homeViewModel, detailViewModel)
               // DetailScreen(viewModel = detailViewModel)
            }
        }
    }
}

