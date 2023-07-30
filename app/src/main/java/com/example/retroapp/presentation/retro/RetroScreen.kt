package com.example.retroapp.presentation.retro

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.retroapp.R
import com.example.retroapp.navigation.ROUTE_CHAT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetroScreen(
    viewModel: RetroViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController
) {
    var meetingTitle by remember { mutableStateOf("") }
    var meetingHours by remember { mutableStateOf("") }


    val activeStatus by viewModel.activeStatus.collectAsState()
    if (!activeStatus) {
            Card(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth(1f).padding(15.dp, 100.dp, 15.dp, 5.dp)
                    .border(
                        2.dp, colorResource(id = R.color.blue),
                        shape = RoundedCornerShape(15.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Yeni Bir Retro Toplantısı Oluşturun",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        value = meetingTitle,
                        onValueChange = { meetingTitle = it },
                        label = { Text("Toplantı Başlığı") },
                        modifier = Modifier.align(CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    OutlinedTextField(

                        value = meetingHours,
                        onValueChange = { meetingHours = it },
                        label = { Text("Toplantı Süresi") },
                        placeholder = { Text("00:00") }, // Set the hint here
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions.Default,
                        modifier = Modifier.align(CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /*  Text(
                    text = "Toplantı Sahibi"//retroViewModel.getMeetingOwnerName(),
                )*/
                    Button(
                        onClick = {
                            //  onDismiss()
                        },
                        modifier = Modifier
                            .size(200.dp, 60.dp)
                            .padding(10.dp, 5.dp, 5.dp, 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.blue),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "İptal Et",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                            //onConfirm()
                            // Kullanıcı toplantı süresini onayladığında geri sayım sayacını başlatmak için
                            // retroViewModel.startCountDownTimer(meetingHours, meetingMinutes)
                            // val totalMinutes = meetingHours * 60 + meetingMinutes
                            //val totalSeconds = totalMinutes * 60
                            // meetingHours=convertToHourMinuteFormat(meetingHours)
                         //   meetingHours = convertToHourMinuteFormat(meetingHours)
                           // Log.d("CustomDialog", "Toplantı Süresi: $meetingHours")
                        },
                        modifier = Modifier
                            .size(200.dp, 60.dp)
                            .padding(0.dp, 5.dp, 10.dp, 10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.blue),
                            contentColor = Color.White
                        )
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
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                if (activeStatus) {
                    navController.navigate(ROUTE_CHAT)
                    Log.d("chat","navigate")
                } else {
                    viewModel.createRetro(listOf(), true, meetingTitle, meetingHours.toInt(), onComplete = {
                        navController.navigate(ROUTE_CHAT)
                    })
                    }
            },
            modifier = Modifier
                .padding(10.dp,10.dp,10.dp,150.dp).align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue),
                contentColor = Color.White
            )
        ) {
            if (activeStatus) {
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
fun convertToHourMinuteFormat(input: String): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = sdf.parse(input)
    val hourMinuteFormat = SimpleDateFormat("hh:mm", Locale.getDefault())
    return hourMinuteFormat.format(date)
}