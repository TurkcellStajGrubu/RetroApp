package com.example.retroapp.presentation.home

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
import com.example.retroapp.presentation.auth.AuthViewModel
import com.example.retroapp.presentation.ui.theme.DarkBlue
import com.example.retroapp.presentation.ui.theme.Yellow

@Composable
fun LogoutDialog(
    authViewModel: AuthViewModel,
    onDismiss: () -> Unit,
    navController: NavHostController
) {
    AlertDialog(modifier = Modifier.background(color= DarkBlue,shape = RoundedCornerShape(size = 40.dp)),
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Logout", color = DarkBlue)
        },
        text = {
            Text(text = "Do you want to log out?")
        },
        confirmButton = {
            Button(modifier = Modifier.size(160.dp,40.dp), colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                onClick = {
                    authViewModel.logout(navController)
                    onDismiss()
                },
            ) {
                Text(text = "Logout", color = DarkBlue)
            }
        },
        dismissButton = {
            Button(modifier = Modifier .border(1.dp, Yellow, shape = RoundedCornerShape(size = 40.dp)) .size(100.dp, 38.dp),
                colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent ),
                onClick = onDismiss
            ) {
                Text(text = "Cancel", color = DarkBlue)
            }
        }
    )
}