package com.example.retroapp.presentation.retro.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.retroapp.R
import com.example.retroapp.navigation.ROUTE_HOME

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    navController: NavController,
    meetingTitle: String,
    adminName: String,
) {

    //   val selectedImageUris = rememberSaveable() { mutableStateOf<List<Uri>>(emptyList()) }
    Scaffold(modifier = Modifier
        .padding(10.dp)
        .background(Color.White),

        topBar = {
            TopBar(
                navController,
                meetingTitle = chatViewModel.meetingTitle.value ?: "",
                adminName = chatViewModel.adminName.value ?: ""
            )
        },
        bottomBar = {
            BottomBar()
        },

    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = Color.White)
                .padding(10.dp, contentPadding.calculateTopPadding(), 15.dp, bottom = 15.dp)
                .fillMaxSize()
        ) {

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, adminName: String, meetingTitle: String) {
    TopAppBar(
        modifier = Modifier.background(Color.White),
        title = {
            Text(text = meetingTitle, fontSize = 16.sp, style = MaterialTheme.typography.titleMedium,)
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .background(colorResource(id = R.color.wred), shape = RoundedCornerShape(5.dp)),
                onClick = {
                    navController.navigate(ROUTE_HOME)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colorResource(id = R.color.dred)
                )
            }
        },
        actions = {
            Text(
                text = adminName,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 16.dp),
                color = Color.Black
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun BottomBar() {
    val selectedOption = rememberSaveable() { mutableStateOf("Select Type") }
    val comment = rememberSaveable() { mutableStateOf("") }
    val contextForToast = LocalContext.current.applicationContext

    Scaffold(modifier = Modifier
        .padding(10.dp)
        .background(Color.White)
        .size(450.dp, 600.dp)


    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = Color.White)
                .padding(10.dp, contentPadding.calculateTopPadding(), 15.dp, bottom = 15.dp)
                .fillMaxSize()

        ) {

            Box(modifier = Modifier.fillMaxWidth(1F)) {
                Row(
                    modifier = Modifier.fillMaxWidth(1F),
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = selectedOption.value == "Option 1",
                        onClick = { selectedOption.value = "Option 1" },
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.blue), // Seçili durumda içeriğin rengi
                            unselectedColor = Color.Black // Seçili olmadığında içeriğin rengi
                        )
                    )
                    Text(stringResource(id = R.string.iyi_giden), modifier = Modifier.align(CenterVertically), fontSize = 14.sp)
                    // Spacer(modifier = Modifier.width(15.dp))
                    RadioButton(
                        selected = selectedOption.value == "Option 2",
                        onClick = { selectedOption.value = "Option 2" },
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.blue), // Seçili durumda içeriğin rengi
                            unselectedColor = Color.Black // Seçili olmadığında içeriğin rengi
                        )
                    )
                    Text(
                        stringResource(id = R.string.gelistirilmesi_gereken),
                        modifier = Modifier.align(CenterVertically), fontSize = 14.sp
                    )
                }
            }

            Row() {
                OutlinedTextField(

                    value = comment.value,
                    onValueChange = { comment.value = it },
                    label = { Text("Comment", color = Color.Black, fontSize = 14.sp) },
                    modifier = Modifier
                        .padding(1.dp, 1.dp, 1.dp, 5.dp),
                    maxLines = 6,
                    colors= TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.blue), // Odaklanıldığında kenar çizgisi rengi
                        unfocusedBorderColor = Color.Black, // Odak dışında kenar çizgisi rengi
                        focusedLabelColor = Color.Black, // Odaklanıldığında etiket rengi
                        unfocusedLabelColor = Color.Black // Odak dışında etiket rengi
                    )
                )

                Box(
                    modifier = Modifier
                        .size(57.dp, 62.dp)
                        .align(Bottom)
                        .padding(start = 2.dp, bottom = 5.dp)
                        .background(
                            colorResource(id = R.color.blue),
                            shape = RoundedCornerShape(5.dp),
                        ),

                ) {
                    IconButton( modifier = Modifier.align(Center),
                        onClick = {

                        }) {
                        Icon(
                            tint = Color.White,
                            painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                            contentDescription = "Add Comment Icon",)
                    }
                }

            }
        }
    }

}

/*
@Preview
@Composable
fun Review() {
    val chatViewModel = ChatViewModel()
    val navController = rememberNavController()
    ChatScreen(
        chatViewModel = chatViewModel,
        navController = navController,
        adminName = "", meetingTitle = ""
    )
}*/