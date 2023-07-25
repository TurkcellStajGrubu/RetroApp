package com.example.retroapp.presentation.retro.register

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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retroapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RetroRegisterScreen() {
    val selectedOption = rememberSaveable() { mutableStateOf("Select Type") }
    val comment = rememberSaveable() { mutableStateOf("") }
    val contextForToast = LocalContext.current.applicationContext
 //   val selectedImageUris = rememberSaveable() { mutableStateOf<List<Uri>>(emptyList()) }
    Scaffold(modifier = Modifier.padding(10.dp).background(Color.White),
        topBar = {
            TopBar()
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(10.dp, contentPadding.calculateTopPadding(), 15.dp)
                .fillMaxSize()
        ) {
            /*Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(1F)
                    .padding(0.dp, 5.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Retro Toplantısı", modifier = Modifier
                    .align(Start)
                    .padding(15.dp))
            }*/
            OutlinedTextField(

                value = comment.value,
                onValueChange = { comment.value = it },
                label = { Text("Comment", color = Color.Black, fontSize = 14.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),
                maxLines = 6,
                colors= TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.blue), // Odaklanıldığında kenar çizgisi rengi
                    unfocusedBorderColor = Color.Black, // Odak dışında kenar çizgisi rengi
                    focusedLabelColor = Color.Black, // Odaklanıldığında etiket rengi
                    unfocusedLabelColor = Color.Black // Odak dışında etiket rengi
                )
            )
            Spacer(modifier = Modifier.height(7.dp))
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
                    Text("İyi Giden İşler", modifier = Modifier.align(CenterVertically), fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(15.dp))
                    RadioButton(
                        selected = selectedOption.value == "Option 2",
                        onClick = { selectedOption.value = "Option 2" },
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.blue), // Seçili durumda içeriğin rengi
                            unselectedColor = Color.Black // Seçili olmadığında içeriğin rengi
                        )
                    )
                    Text(
                        "Geliştirilmesi Gereken İşler",
                        modifier = Modifier.align(CenterVertically), fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Selected Option: ${selectedOption.value}", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))
            //PickImageFromGallery(selectedImages = selectedImageUris, viewModel = viewModel)
            Button(
                onClick = {
                    Toast.makeText(
                        contextForToast,
                        "Comment succesfully added.",
                        Toast.LENGTH_LONG
                    ).show()
                },
                modifier = Modifier.fillMaxWidth(1F),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.blue),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Add Comment")
            }
            Text("Comment: ${selectedOption.value} => ${comment.value}", fontSize = 14.sp)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar( ) {


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
@Preview
@Composable
fun Review() {
    RetroRegisterScreen()
}