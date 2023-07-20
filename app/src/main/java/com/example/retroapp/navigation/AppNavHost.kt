package com.example.retroapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.retroapp.presentation.auth.AuthViewModel
import com.example.retroapp.presentation.auth.LoginScreen
import com.example.retroapp.presentation.auth.SignupScreen
import com.example.retroapp.presentation.detail.DetailScreen
import com.example.retroapp.presentation.detail.DetailViewModel
import com.example.retroapp.presentation.home.HomeScreen
import com.example.retroapp.presentation.home.HomeViewModel
import com.example.retroapp.presentation.menu.navigation.Navigation

@Composable
fun AppNavHost(
    viewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOGIN
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(viewModel, navController)
        }
        composable(ROUTE_SIGNUP) {
            SignupScreen(viewModel, navController)
        }
        composable(ROUTE_HOME) {
            Navigation("Home", viewModel, homeViewModel, navController)
            HomeScreen(
                viewModel = homeViewModel,
                onCardClick = {navController.navigate(ROUTE_DETAIL)},
                onFabClick = { navController.navigate(ROUTE_ADD) },
                onLogoutClick = {},
                navController = navController,
            )
        }

        composable(ROUTE_DETAIL) { // Assuming ROUTE_DETAIL is the route name for DetailScreen
            DetailScreen(detailViewModel,true, navController)
        }
        composable(ROUTE_ADD){
            DetailScreen(viewModel = detailViewModel, isDetail = false, navController)
        }
    }
}