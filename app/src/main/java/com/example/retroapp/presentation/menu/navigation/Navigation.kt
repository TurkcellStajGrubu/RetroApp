package com.example.retroapp.presentation.menu.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.retroapp.R
import com.example.retroapp.data.CardItem
import com.example.retroapp.presentation.auth.AuthViewModel
import com.example.retroapp.presentation.home.HomeScreen
import com.example.retroapp.presentation.retro.AlertDialogViewModel
import com.example.retroapp.presentation.retro.RetroScreen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(
    name: String,
    viewModel: AuthViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val items = listOf("HomeScreen", "RetroScreen")
    val selectedPage = remember { mutableStateOf(0) }

    val cardItems = listOf(
        CardItem("İbrahim TAŞKIN", "2023-07-18", "Note 1", "Type A"),
        CardItem("Orhan UÇAR", "2023-07-19", "Note 2", "Type B"),
        CardItem("Merve OKTAY", "2023-07-20", "Note 3", "Type C"),
        CardItem("Ali Erdem ALKOÇ", "2023-07-21", "Note 4", "Type D")
    )
    Scaffold(
        content = {
            when (selectedPage.value) {
                0 -> HomeScreen(
                cardItems =  cardItems ,
                onCardClick = {}, // Provide appropriate onCardClick behavior here
                onFabClick = {}, // Provide appropriate onFabClick behavior here
                onLogoutClick = {} // Provide appropriate onLogoutClick behavior here
                )
                1 -> RetroScreen(AlertDialogViewModel())
            }
        },
        bottomBar = {
            NavigationBar() {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedPage.value == index,
                        onClick = { selectedPage.value = index },
                        label = { Text(text = item) },
                        icon = {
                            when (item) {
                                "HomeScreen" -> Icon(
                                    painter = painterResource(id = R.drawable.ic_home_icon),
                                    contentDescription = ""
                                )
                                "RetroScreen" -> Icon(
                                    painter = painterResource(id = R.drawable.ic_retro_icon),
                                    contentDescription = ""
                                )
                            }
                        }
                    )
                }
            }
        }
    )
}
