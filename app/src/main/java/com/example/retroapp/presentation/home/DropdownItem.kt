package com.example.retroapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.retroapp.R
import com.example.retroapp.presentation.auth.AuthViewModel

@Composable
fun DropdownItem(
    mDisplayMenu:MutableState<Boolean>,
    filterType:MutableState<String>,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    var isLogoutDialogOpen by remember { mutableStateOf(false) }

    DropdownMenu(
        expanded = mDisplayMenu.value,
        onDismissRequest = { mDisplayMenu.value = false },
        Modifier.background(Color.White)
    ) {
        DropdownMenuItem(
            onClick = {
                filterType.value = ""
                mDisplayMenu.value = false
            },
            text = {
                Text(
                    text = "Filtrelemeyi İptal Et",
                    fontSize = 16.sp,
                    style = TextStyle.Default
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = Color.Red
                )
            }
        )
        DropdownMenuItem(
            onClick = {
                filterType.value = "Teknik Karar Toplantısı"
                mDisplayMenu.value = false
            },
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
            onClick = {
                filterType.value = "Retro Toplantısı"
                mDisplayMenu.value = false
            },
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
            onClick = {
                filterType.value = "Cluster Toplantısı"
                mDisplayMenu.value = false
            },
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
                    tint = colorResource(id = R.color.blue)
                )
            }
        )
        DropdownMenuItem(
            onClick = {
                isLogoutDialogOpen = true
            },
            text = { Text(text = "Logout", fontSize = 16.sp, style = TextStyle.Default) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                )
            }
        )

        if (isLogoutDialogOpen) {
            LogoutDialog(
                authViewModel = authViewModel,
                navController = navController,
                onDismiss = {
                    isLogoutDialogOpen = false
                }
            )
        }
    }
}