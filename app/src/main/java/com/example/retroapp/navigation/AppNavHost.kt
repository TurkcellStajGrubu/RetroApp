package com.example.retroapp.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.retroapp.presentation.auth.AuthViewModel
import com.example.retroapp.presentation.auth.LoginScreen
import com.example.retroapp.presentation.auth.SignupScreen
import com.example.retroapp.presentation.detail.DetailScreen
import com.example.retroapp.presentation.detail.DetailViewModel
import com.example.retroapp.presentation.home.HomeScreen
import com.example.retroapp.presentation.home.HomeViewModel
import com.example.retroapp.presentation.menu.navigation.Navigation
import com.example.retroapp.presentation.retro.AlertDialogViewModel

@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel,
    alertDialogViewModel: AlertDialogViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOGIN
) {

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (isLoggedIn) ROUTE_HOME else startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(viewModel, navController)
        }
        composable(ROUTE_SIGNUP) {
            SignupScreen(viewModel, navController)
        }

        composable(ROUTE_HOME) {
            Navigation("Home", viewModel, homeViewModel, navController, modifier, alertDialogViewModel )
        }

        composable("detail/{note_id}", arguments = listOf(navArgument("note_id"){
            type = NavType.StringType
        })) { // Assuming ROUTE_DETAIL is the route name for DetailScreen
            DetailScreen(detailViewModel,true, navController, noteId = it.arguments?.getString("note_id") as String)
        }
        composable(ROUTE_ADD){
            DetailScreen(viewModel = detailViewModel, isDetail = false, navController, "")
        }

    }
}
