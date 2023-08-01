package com.example.retroapp.presentation.retro.chat

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.example.retroapp.R
import com.example.retroapp.navigation.ROUTE_HOME

@Composable
fun ExitMeetingDialog(
    onDismiss: () -> Unit,
    chatViewModel: ChatViewModel,
    navController: NavHostController,
    dialogText: String,
    isAdmin: Boolean
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            if (isAdmin)
                Text(text = "Toplantıyı Sonlandır")
            else
                Text(text = "Toplantıdan Ayrıl")
        },
        text = {
            Text(text = dialogText)
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isAdmin) {
                        onDismiss()
                        chatViewModel.updateRetroTime(0)
                    } else {
                        onDismiss()
                        navController.navigate(ROUTE_HOME)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Evet")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.blue),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Hayır")
            }
        }
    )
}
