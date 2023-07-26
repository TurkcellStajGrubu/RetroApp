package com.example.retroapp.presentation.retro.chat

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.retroapp.R
import com.example.retroapp.navigation.ROUTE_HOME


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    navController: NavController,
) {

    //   val selectedImageUris = rememberSaveable() { mutableStateOf<List<Uri>>(emptyList()) }
    Scaffold(modifier = Modifier
        .padding(10.dp)
        .background(Color.White),

        topBar = {
            TopBar(navController)
        },
        bottomBar = {
            BottomBar()
        }


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
fun TopBar( navController: NavController) {


    TopAppBar(
        modifier = Modifier.background(Color.White),
        title = {
            Text(
                text = "Add Comment", fontSize = 16.sp
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(ROUTE_HOME)

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
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

                Button(
                    onClick = {
                        Toast.makeText(
                            contextForToast,
                            "Comment successfully added.",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    modifier = Modifier
                        .size(57.dp,62.dp)
                        .align(Bottom)
                        .padding(start = 2.dp, bottom = 5.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.blue),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.message_vector_asset),
                        contentDescription = "Add Comment Icon",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }

        }
    }

}


@Preview
@Composable
fun Review() {
    val chatViewModel = ChatViewModel()

    // NavController oluşturun, burada uygun bir şekilde başlatılmalı
    val navController = rememberNavController()

    // ChatScreen'e gerekli parametreleri geçin
    ChatScreen(chatViewModel = chatViewModel, navController = navController)
}