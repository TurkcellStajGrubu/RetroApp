package com.example.retroapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.retroapp.R
@Composable
fun DropdownItem( mDisplayMenu:MutableState<Boolean>, filterType:MutableState<String>,onLogoutClick: () -> Unit) {

    DropdownMenu(
        expanded = mDisplayMenu.value,
        onDismissRequest = { mDisplayMenu.value = false },
        Modifier.background(Color.White)
    ) {
        DropdownMenuItem(
            onClick = {},
            text = { Text(text = "Home", fontSize = 16.sp, style = TextStyle.Default) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                )
            }
        )
        DropdownMenuItem(
            onClick = { filterType.value = "Teknik Karar Toplantısı" },
            text = {
                Text(
                    text = "Teknik Karar Toplantısı",
                    fontSize = 16.sp,
                    style = TextStyle.Default
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.green_circle_icon),
                    contentDescription = null,
                    tint = colorResource(id = R.color.green)
                )
            }
        )
        DropdownMenuItem(
            onClick = { filterType.value = "Retro Toplantısı" },
            text = { Text(text = "Retro Toplantısı", fontSize = 16.sp, style = TextStyle.Default) },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.yellow_circle_icon),
                    contentDescription = null,
                    tint = colorResource(id = R.color.yellow)
                )
            }
        )
        DropdownMenuItem(
            onClick = { filterType.value = "Cluster Toplantısı" },
            text = {
                Text(
                    text = "Cluster Toplantısı",
                    fontSize = 16.sp,
                    style = TextStyle.Default
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.blue_circle_icon),
                    contentDescription = null,
                    tint = colorResource(id = R.color.button_color)
                )
            }
        )
        DropdownMenuItem(
            onClick = {
                onLogoutClick()
            },
            text = { Text(text = "Logout", fontSize = 16.sp, style = TextStyle.Default) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                )
            }
        )
    }
}