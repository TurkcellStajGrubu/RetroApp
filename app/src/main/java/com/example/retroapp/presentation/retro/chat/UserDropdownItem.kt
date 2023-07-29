package com.example.retroapp.presentation.retro.chat

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.retroapp.R

@Composable
fun UserDropdownItem(
    mDisplayMenu: MutableState<Boolean>,navController: NavHostController
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val dialogText="Toplantıdan ayrılmak istediğinize emin misiniz?"
    DropdownMenu(
        expanded = mDisplayMenu.value,
        onDismissRequest = { mDisplayMenu.value = false },
        Modifier.background(Color.White),
    ) {

        DropdownMenuItem(
            onClick = {
                mDisplayMenu.value = false
                setShowDialog(true)
            },
            text = { GetText(R.string.toplantidan_ayril) },
        )
    }

    if (showDialog) {
        ExitMeetingDialog(onDismiss = {setShowDialog(false)}, navController = navController, dialogText =dialogText )
    }
}

@Composable
private fun GetText(typeString:Int){
    Text(
        text =  stringResource(id = typeString),
        fontSize = 16.sp,
        style = TextStyle.Default
    )
}