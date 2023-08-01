package com.example.retroapp.presentation.home

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.retroapp.presentation.auth.AuthViewModel

@Composable
fun LogoutDialog(
    authViewModel: AuthViewModel,
    onDismiss: () -> Unit,
    navController: NavHostController
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Logout")
        },
        text = {
            Text(text = "Do you want to log out?")
        },
        confirmButton = {
            Button(
                onClick = {
                    authViewModel.logout(navController)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Logout")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(text = "Cancel")
            }
        }
    )
}