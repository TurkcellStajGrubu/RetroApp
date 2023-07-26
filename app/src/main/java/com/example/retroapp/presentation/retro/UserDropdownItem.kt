package com.example.retroapp.presentation.retro

import androidx.compose.foundation.background
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.retroapp.R

@Composable
fun UserDropdownItem(
    mDisplayMenu: MutableState<Boolean>,
) {
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

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
        AlertDialog(
            onDismissRequest = { setShowDialog(false) },
            title = { Text("Toplantıdan Ayrıl") },
            text = { Text("Toplantıdan ayrılmak istediğinize emin misiniz?") },
            confirmButton = {
                Button(onClick = {
                    setShowDialog(false)
                    /**
                     * Toplantıdan ayrılınca yapılması gereken işlemler yapılacak.
                     */
                }) {
                    Text("Evet")
                }
            },
            dismissButton = {
                Button(onClick = { setShowDialog(false) }) {
                    Text("Hayır")
                }
            }
        )
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