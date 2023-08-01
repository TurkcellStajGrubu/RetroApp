package com.example.retroapp.presentation.retro

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.retroapp.navigation.ROUTE_CHAT
import com.example.retroapp.presentation.ui.theme.DarkBlue
import com.example.retroapp.presentation.ui.theme.LightGray
import com.example.retroapp.presentation.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetroScreen(
    viewModel: RetroViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController
) {
    var meetingTitle by remember { mutableStateOf("") }
    var meetingHours by remember { mutableStateOf("") }
    val focusRequesterTitle = remember { FocusRequester() }
    val focusRequesterHours = remember { FocusRequester() }
    val isTitleFocused = remember { mutableStateOf(false) }
    val isHoursFocused = remember { mutableStateOf(false) }
    val activeStatus by viewModel.activeStatus.collectAsState()
    val isPrepare = remember { mutableStateOf(true) }
    val contextForToast = LocalContext.current.applicationContext
    Scaffold(
        topBar = {
            TopBar()
        }
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {


            if (!activeStatus) {
                Card(
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(15.dp, 200.dp, 15.dp, 5.dp)
                        .border(
                            2.dp, DarkBlue,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .background(Color.White),
                    colors = CardDefaults.cardColors(Color.White)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalAlignment = CenterHorizontally
                    ) {

                        Text(
                            text = "Yeni Bir Retro Toplantısı Oluşturun",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            color = DarkBlue
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        TextField(
                            value = meetingTitle,
                            onValueChange = { meetingTitle = it },
                            label = {
                                Text(
                                    "Toplantı Başlığı",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            },
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .focusRequester(focusRequesterTitle)
                                .onFocusChanged { isTitleFocused.value = it.isFocused }
                                .background(LightGray),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                placeholderColor = Color.Gray,
                                cursorColor = DarkBlue,
                                focusedBorderColor = DarkBlue,
                                unfocusedBorderColor = Color.Gray
                            ),
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        TextField(
                            value = meetingHours,
                            onValueChange = { meetingHours = it },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    "Toplantı Süresi",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            },
                            placeholder = {
                                Text(
                                    "Süreyi Dakika Cinsinden Giriniz.",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                            }, // Set the hint here
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .focusRequester(focusRequesterHours)
                                .onFocusChanged { isHoursFocused.value = it.isFocused }
                                .background(LightGray),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = Color.Black,
                                placeholderColor = Color.Gray,
                                cursorColor = DarkBlue,
                                focusedBorderColor = DarkBlue,
                                unfocusedBorderColor = Color.Gray
                            ),
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
                                if (isHoursFocused.value) {
                                    focusRequesterTitle.requestFocus()//Zamanda olan imleci title a taşır
                                }
                                meetingHours = ""
                                meetingTitle = ""
                            },
                            modifier = Modifier
                                .size(150.dp, 60.dp)
                                .padding(10.dp, 5.dp, 5.dp, 10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                        ) {
                            Text(
                                text = "İptal Et",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = DarkBlue
                            )
                        }
                        if (isPrepare.value) {
                            Button(
                                onClick = {
                                    if (meetingTitle.isEmpty()) {
                                        Toast.makeText(
                                            contextForToast,
                                            "Title cannot be empty",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else if (meetingHours.isEmpty()) {
                                        Toast.makeText(
                                            contextForToast,
                                            "Time cannot be empty",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        isPrepare.value = false
                                        viewModel.createRetro(
                                            listOf(),
                                            true,
                                            meetingTitle,
                                            meetingHours.toInt(),
                                            onComplete = {
                                                navController.navigate(ROUTE_CHAT)
                                            })
                                    }

                                },
                                modifier = Modifier
                                    .size(200.dp, 60.dp)
                                    .padding(0.dp, 5.dp, 10.dp, 10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                            ) {
                                Text(
                                    text = "Toplantı Başlat",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = DarkBlue
                                )
                            }
                        }

                    }
                }
            } else {
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (isPrepare.value) {
                        Button(
                            onClick = {
                                if (activeStatus) {
                                    isPrepare.value = false
                                    navController.navigate(ROUTE_CHAT)
                                } else {
                                    viewModel.createRetro(
                                        arrayListOf(),
                                        true,
                                        meetingTitle,
                                        meetingHours.toInt(),
                                        onComplete = {
                                            navController.navigate(ROUTE_CHAT)
                                        })
                                }
                            },
                            modifier = Modifier
                                .padding(10.dp, 10.dp, 10.dp, 150.dp)
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                        ) {
                            if (activeStatus) {
                                Log.d("aktif", "aktif")
                                Text(
                                    text = "Toplantıya Katıl",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    color = DarkBlue
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
          title={Text(
              text = "Retro Meeting",color= Color.White
          )},
          colors = TopAppBarDefaults.largeTopAppBarColors(DarkBlue)
    )
}
