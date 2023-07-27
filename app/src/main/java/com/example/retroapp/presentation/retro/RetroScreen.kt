package com.example.retroapp.presentation.retro

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetroScreen(
    viewModel: RetroViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val title = rememberSaveable() { mutableStateOf("") }
    val time = rememberSaveable() { mutableStateOf("") }
    val activeStatus by viewModel.activeStatus.collectAsState()
    val activeRetroStatus by viewModel.activeRetroIdState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (!activeStatus) {
            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it },
                label = { Text("Title", color = Color.Black) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
            )
            OutlinedTextField(
                value = time.value,
                onValueChange = { time.value = it },
                label = { Text("Time", color = Color.Black) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp)
            )
        }
        Button(
            onClick = {
                if (activeStatus){
                    Log.d("retroId", activeRetroStatus)
                Log.d("chatkatıl","navigate")
                }
                else {
                    viewModel.createRetro(listOf(),true, title.value, time.value.toInt(), onComplete = {Log.d("chat","navigate") })
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