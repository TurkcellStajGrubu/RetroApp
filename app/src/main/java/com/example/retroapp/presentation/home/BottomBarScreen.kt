package com.example.retroapp.presentation.home

import com.example.retroapp.R

sealed class BottomBarScreen(
    val route:String,
    val title:String,
    val icon: Int
){
    object Home : BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.ic_home_icon
    )

    object Profile : BottomBarScreen(
        route = "retro",
        title = "Retro",
        icon = R.drawable.ic_retro_icon
    )

}