package com.example.retroapp.presentation.retro

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.retroapp.presentation.detail.DetailViewModel
import com.google.android.play.integrity.internal.l

@Composable
fun RetroScreen(
    viewModel: RetroViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val prepareStatus by viewModel.prepareStatus.collectAsState()
    val activeStatus by viewModel.activeStatus.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = {
                if (activeStatus){
                    viewModel.getRetro("")
                }else if (prepareStatus){
                    Log.d("Prepare", "Hazırlanıyor")
                }else {
                    viewModel.createRetro(listOf(), listOf(), true, false, 0, onComplete = {})
                }

            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(10.dp),
            shape = CircleShape
        ) {
            if (activeStatus){
                Log.d("aktif", "aktif")
                Text(
                    text = "Toplantıya Katıl",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            } else {
                if (prepareStatus){
                    Log.d("hazırlık", "hazırlık")
                    Text(
                        text = "Toplantı Hazırlanıyor",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                } else {
                    Log.d("yok", "yok")
                    Text(
                        text = "Yeni Toplantı Başlat",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
            }

        }
    }
    if (viewModel.isDialogShown) {
        CustomDialog(
            onDismiss = {
                viewModel.onDismissDialog()
            },
            onConfirm = {
                //viewmodel.buyItem()
            },
            retroViewModel = viewModel
        )
    }
}