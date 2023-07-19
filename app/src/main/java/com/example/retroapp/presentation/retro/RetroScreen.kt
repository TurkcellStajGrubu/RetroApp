package com.example.retroapp.presentation.retro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RetroScreen(
    viewModel: AlertDialogViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = {
                viewModel.onPurchaseClick()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(10.dp),
            shape = CircleShape
        ) {
            Text(
                text = "Yeni Toplantı Başlat",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
        }
    }
    if(viewModel.isDialogShown){
        CustomDialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                //viewmodel.buyItem()
            }
        ) {
            //viewmodel.buyItem()
        }
    }
}