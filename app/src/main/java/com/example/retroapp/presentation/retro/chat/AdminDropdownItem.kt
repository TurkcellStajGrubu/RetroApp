package com.example.retroapp.presentation.retro.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.retroapp.R
import com.example.retroapp.presentation.ui.theme.DarkBlue
import com.example.retroapp.presentation.ui.theme.LightGray
import com.example.retroapp.presentation.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDropdownItem(
    mDisplayMenu: MutableState<Boolean>,
    navController: NavHostController,
    chatViewModel: ChatViewModel
) {
    val (dialogType, setDialogType) = remember { mutableStateOf("") }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val (inputTime, setInputTime) = remember { mutableStateOf("") }
    val dialogText = "Toplantıyı sonlandırmak istediğinize emin misiniz?"
    val contextForToast = LocalContext.current.applicationContext

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
            text = { GetText(R.string.toplanti_suresi_güncelle) },
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
            "extend", "reduce" -> AlertDialog(modifier = Modifier.background(color= DarkBlue,shape = RoundedCornerShape(size = 40.dp)),
                onDismissRequest = { setShowDialog(false) },
                title = { Text("Süreyi güncelle", color = DarkBlue) },
                text = {
                    TextField(
                        value = inputTime,
                        onValueChange = { setInputTime(it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Süre (dakika)")},
                        modifier = Modifier.background(LightGray),
                        colors = TextFieldDefaults.outlinedTextFieldColors( textColor = Color.Black, placeholderColor = Color.Gray, cursorColor = DarkBlue, focusedBorderColor = DarkBlue, unfocusedBorderColor = Color.Gray),
                    )
                },
                confirmButton = {
                    Button(modifier = Modifier.size(160.dp,40.dp), colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                        onClick = {
                        val newTime = inputTime.toIntOrNull()
                        if (newTime != null) {
                            chatViewModel.updateRetroTime(newTime)
                        } else{
                            Toast.makeText(contextForToast, "New time cannot be empty!", Toast.LENGTH_LONG).show()
                        }
                        setShowDialog(false)
                    }) {
                        Text("Onayla", color = DarkBlue)
                    }
                },
                dismissButton = {
                    Button(modifier = Modifier .border(1.dp, Yellow, shape = RoundedCornerShape(size = 40.dp)) .size(100.dp, 38.dp),
                        colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent ),
                        onClick = { setShowDialog(false) }) {
                        Text("İptal", color = DarkBlue)
                    }
                }
            )

            "end" -> ExitMeetingDialog(
                onDismiss = { setShowDialog(false) },
                chatViewModel,
                navController = navController,
                dialogText = dialogText,
                true
            )
        }
    }
}

@Composable
private fun GetText(typeString: Int) {
    Text(
        text = stringResource(id = typeString),
        fontSize = 16.sp,
        style = TextStyle.Default
    )
}
