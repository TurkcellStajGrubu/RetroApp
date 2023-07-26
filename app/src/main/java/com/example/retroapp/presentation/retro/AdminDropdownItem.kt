package com.example.retroapp.presentation.retro

import androidx.compose.foundation.background
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDropdownItem(
    mDisplayMenu: MutableState<Boolean>,
) {
    val (dialogType, setDialogType) = remember { mutableStateOf("") }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val (inputTime, setInputTime) = remember { mutableStateOf("") }

    DropdownMenu(
        expanded = mDisplayMenu.value,
        onDismissRequest = { mDisplayMenu.value = false },
        Modifier.background(Color.White),
    ) {

        DropdownMenuItem(
            onClick = {
                mDisplayMenu.value = false
                setDialogType("extend")
                setShowDialog(true)
            },
            text = { GetText(R.string.toplanti_suresi_uzat) },
        )

        DropdownMenuItem(
            onClick = {
                mDisplayMenu.value = false
                setDialogType("reduce")
                setShowDialog(true)
            },
            text = { GetText(R.string.toplanti_suresi_kisalt) },
        )

        DropdownMenuItem(
            onClick = {
                mDisplayMenu.value = false
                setDialogType("end")
                setShowDialog(true)
            },
            text = { GetText(R.string.toplantiyi_sonlandir) },
        )
    }

    if (showDialog) {
        when (dialogType) {
            "extend", "reduce" -> AlertDialog(
                onDismissRequest = { setShowDialog(false) },
                title = { Text("Süreyi güncelle") },
                text = {
                    TextField(
                        value = inputTime,
                        onValueChange = { setInputTime(it) },
                        label = { Text("Süre (dakika)") }
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        setShowDialog(false)
                        /**
                         * Süreyi güncellerken yapacağımız işlemleri buraya eklicez.
                         */
                    }) {
                        Text("Onayla")
                    }
                },
                dismissButton = {
                    Button(onClick = { setShowDialog(false) }) {
                        Text("İptal")
                    }
                }
            )
            "end" -> AlertDialog(
                onDismissRequest = { setShowDialog(false) },
                title = { Text("Toplantıyı Sonlandır") },
                text = { Text("Toplantıyı sonlandırmak istediğinize emin misiniz?") },
                confirmButton = {
                    Button(onClick = {
                        setShowDialog(false)
                        /**
                         * Toplantıyı sonlandırınca olacak işlemleri buraya eklicez.
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
}

@Composable
private fun GetText(typeString:Int){
    Text(
        text =  stringResource(id = typeString),
        fontSize = 16.sp,
        style = TextStyle.Default
    )
}



@Preview
@Composable
fun PreviewAdminDropdown() {
    val displayMenu = remember { mutableStateOf(true) }
    AdminDropdownItem(mDisplayMenu = displayMenu)
}