package com.example.retroapp.presentation.retro.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.retroapp.navigation.ROUTE_HOME
import com.example.retroapp.presentation.ui.theme.DarkBlue
import com.example.retroapp.presentation.ui.theme.Yellow

@Composable
fun ExitMeetingDialog(
    onDismiss: () -> Unit,
    chatViewModel: ChatViewModel,
    navController: NavHostController,
    dialogText: String,
    isAdmin: Boolean
) {
    AlertDialog(modifier = Modifier.background(color= DarkBlue,shape = RoundedCornerShape(size = 40.dp)),
        onDismissRequest = onDismiss,
        title = {
            if (isAdmin)
                Text(text = "Toplantıyı Sonlandır", color = DarkBlue)
            else
                Text(text = "Toplantıdan Ayrıl", color = DarkBlue)
        },
        text = {
            Text(text = dialogText)
        },
        confirmButton = {
            Button(modifier = Modifier.size(160.dp,40.dp), colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                onClick = {
                    if (isAdmin) {
                        onDismiss()
                        chatViewModel.updateRetroTime(0)
                    } else {
                        onDismiss()
                        navController.navigate(ROUTE_HOME)
                    }
                },
            ) {
                Text(text = "Evet", color = DarkBlue)
            }
        },
        dismissButton = {
            Button(modifier = Modifier .border(1.dp, Yellow, shape = RoundedCornerShape(size = 40.dp)) .size(100.dp, 38.dp),
                colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent ),
                onClick = onDismiss,
            ) {
                Text(text = "Hayır", color = DarkBlue)
            }
        }
    )
}
