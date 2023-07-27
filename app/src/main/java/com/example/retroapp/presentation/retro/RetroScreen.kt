package com.example.retroapp.presentation.retro

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetroScreen(
    viewModel: RetroViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController,
    retroViewModel :RetroViewModel
) {
    // Collecting state from the ViewModel
    val prepareStatus by retroViewModel.prepareStatus.collectAsState()
    val activeStatus by retroViewModel.activeStatus.collectAsState()
    val retroId by retroViewModel.retroId

    var meetingTitle by remember { mutableStateOf("") }
    var meetingHours by remember { mutableStateOf(0) }
    var meetingMinutes by remember { mutableStateOf(0) }

    if (!retroId.isNullOrBlank()) {
        Log.d("RetroId", retroId!!)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Yeni Bir Retro Toplantısı Oluşturun",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        // Input fields
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = meetingTitle,
                    onValueChange = { meetingTitle = it },
                    label = { Text("Toplantı Adı") }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.size(160.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = meetingHours.toString(),
                        onValueChange = { meetingHours = it.toIntOrNull() ?: 0 },
                        label = { Text("Saat") },
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    TextField(
                        value = meetingMinutes.toString(),
                        onValueChange = { meetingMinutes = it.toIntOrNull() ?: 0 },
                        label = { Text("Dakika") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Start meeting button
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = retroViewModel.getMeetingOwnerName(),
            )

            Button(
                onClick = {
                    retroViewModel.startCountDownTimer(meetingHours, meetingMinutes)
                    val totalMinutes = meetingHours * 60 + meetingMinutes
                    val totalSeconds = totalMinutes * 60

                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = CircleShape
            ) {
                Text(
                    text = "Toplantı Başlat",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
