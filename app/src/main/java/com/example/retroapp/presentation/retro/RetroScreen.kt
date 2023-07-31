package com.example.retroapp.presentation.retro

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.retroapp.R
import com.example.retroapp.navigation.ROUTE_CHAT
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toSet

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RetroScreen(
    viewModel: RetroViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController
) {
    val activeStatus = viewModel.activeStatus
    val activeRetroId by viewModel.activeRetroIdState.collectAsState()
    val recomposeKey = rememberUpdatedState(activeStatus)

    if (!recomposeKey.value.value) {
            IfNotActive(navController = navController, viewModel)
        } else {
            IfActive(navController)
        }

}
@Composable
fun IfActive(navController: NavHostController){
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                navController.navigate(ROUTE_CHAT)
            },
            modifier = Modifier
                .padding(10.dp, 10.dp, 10.dp, 150.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.blue),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Toplantıya Katıl",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IfNotActive(navController: NavHostController,viewModel: RetroViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){
    var meetingTitle by remember { mutableStateOf("") }
    var meetingHours by remember { mutableStateOf("") }
    val focusRequesterTitle = remember { FocusRequester() }
    val focusRequesterHours = remember { FocusRequester() }
    val isTitleFocused = remember { mutableStateOf(false) }
    val isHoursFocused = remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(15.dp, 100.dp, 15.dp, 5.dp)
            .border(
                2.dp, colorResource(id = R.color.blue),
                shape = RoundedCornerShape(15.dp)
            )
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
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))

            OutlinedTextField(
                value = meetingTitle,
                onValueChange = { meetingTitle = it },
                label = { Text("Toplantı Başlığı", fontSize = 14.sp, color = Color.Gray) },
                modifier = Modifier
                    .align(CenterHorizontally)
                    .focusRequester(focusRequesterTitle)
                    .onFocusChanged { isTitleFocused.value = it.isFocused }
            )
            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = meetingHours,
                onValueChange = { meetingHours = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Toplantı Süresi", fontSize = 14.sp, color = Color.Gray) },
                placeholder = {
                    Text(
                        "Süreyi Dakika Cinsinden Giriniz.",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                },
                modifier = Modifier
                    .align(CenterHorizontally)
                    .focusRequester(focusRequesterHours)
                    .onFocusChanged { isHoursFocused.value = it.isFocused }
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (isHoursFocused.value) {
                        focusRequesterTitle.requestFocus()//Zamanda olan imleci title a taşır
                    }
                    meetingHours = ""
                    meetingTitle = ""
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
                    viewModel.createRetro(
                        listOf(),
                        true,
                        meetingTitle,
                        meetingHours.toInt(),
                        onComplete = {
                            navController.navigate(ROUTE_CHAT)
                        })
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