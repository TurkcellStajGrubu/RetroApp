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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
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
    val parentOptions = listOf("Teknik Karar Toplantısı", "Retro Toplantısı", "Cluster Toplantısı")
    DropdownMenu(
        expanded = mDisplayMenu.value,
        onDismissRequest = { mDisplayMenu.value = false },
        Modifier.background(Color.White),
    ) {
        DropdownMenuItem(
            onClick = {
                filterType.value = ""
                mDisplayMenu.value = false
            },
            text = {
                GetText(typeString = R.string.filtre_iptal)
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
                filterType.value = parentOptions[0]
                mDisplayMenu.value = false
            },
            text = { GetText(R.string.teknik_karar) },
            trailingIcon = { GetIcon(R.drawable.green_circle_icon, R.color.green) }
        )
        DropdownMenuItem(
            onClick = {
                filterType.value = parentOptions[1]
                mDisplayMenu.value = false
            },
            text = { GetText(R.string.retro) },
            trailingIcon = { GetIcon(R.drawable.yellow_circle_icon, R.color.yellow) }
        )
        DropdownMenuItem(
            onClick = {
                filterType.value = parentOptions[2]
                mDisplayMenu.value = false
            },
            text = { GetText(R.string.cluster) },
            trailingIcon = { GetIcon(R.drawable.blue_circle_icon,R.color.blue) }
        )
        DropdownMenuItem(
            onClick = {
                isLogoutDialogOpen = true
            },
            text = { GetText(typeString = R.string.logout) },
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
@Composable
private fun GetIcon(typeIcon:Int,typeColor:Int){
    Icon(
        painter = painterResource(id = typeIcon),
        contentDescription = null,
        tint = colorResource(id = typeColor)
    )
}
@Composable
private fun GetText(typeString:Int){
    Text(
        text =  stringResource(id = typeString),
        fontSize = 16.sp,
        style = TextStyle.Default
    )
}