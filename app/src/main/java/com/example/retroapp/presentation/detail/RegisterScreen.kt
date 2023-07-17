package com.example.retroapp.presentation.detail

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    val title = remember { mutableStateOf("") }
    val detail = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(20.dp, 60.dp)
    ) {
       Column(modifier = Modifier
           .align(CenterHorizontally),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = CenterHorizontally
        ) {

           OutlinedTextField(
           value = title.value,
           onValueChange = { title.value = it },
           label = { Text("Title",color=Color.Black) },
           modifier = Modifier
               .fillMaxWidth()
               .padding(1.dp)
          )
           Spacer(modifier = Modifier.height(7.dp))
           DisplaySpinner()
           OutlinedTextField(
               value = detail.value,
               onValueChange = { detail.value = it },
               label = { Text("Detail",color=Color.Black) },
               modifier = Modifier
                   .fillMaxWidth(),
               maxLines = 3
           )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier
            .padding(0.5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = CenterHorizontally
        ) {
            PickImageFromGallery()
            Button(
                onClick = {},
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(1F),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffF57C00),
                    contentColor = Color.White
                )

            ) {
                Text(text = "Add")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySpinner(){
    val parentOptions=listOf("Teknik Karar Toplantısı","Retro Toplantısı","Cluster Toplantısı")
    val expandedState = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf(parentOptions[0]) } //Seçilen toplantı türünü tutuyor

    Column(

        modifier = Modifier
            .fillMaxWidth(1F)
            .padding(1.dp)
            .clickable(onClick = { expandedState.value = true })
    ) {
        TextField(
            value = selectedOption.value,
            modifier = Modifier
                .fillMaxWidth(1F)
                .border(
                    0.5.dp, Color.DarkGray,
                    RoundedCornerShape(5.dp)
                ),

            onValueChange = { /* Değer değiştiğinde yapılacak işlemler */ },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Drop-down",
                    modifier = Modifier.clickable {
                        expandedState.value = !expandedState.value
                    }
                )
            },
            readOnly = true,
            textStyle = TextStyle.Default.copy(fontSize = 16.sp),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        DropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = { expandedState.value = false },Modifier.background(Color.White)
        ) {
            parentOptions.forEach { option ->
                DropdownMenuItem(modifier = Modifier
                    .fillMaxWidth(1F), onClick = {
                        selectedOption.value = option
                        expandedState.value = false
                        Log.d("Option", selectedOption.value)
                    }, text ={Text(text = option, fontSize = 16.sp, style = TextStyle.Default)})
                Divider()
            }
        }
    }
}

@Composable
fun PickImageFromGallery() {
    val selectedImageUris = remember {
        mutableStateOf<List<Uri>>(emptyList())
    }// Dosyadan çekilen resimleri tutuyor
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> selectedImageUris.value = uris }
    )
    LazyRow(
        modifier = Modifier
            .size(500.dp, 130.dp)
            .padding(2.dp)
    ) {

        items(selectedImageUris.value) { uri ->
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp, 120.dp)
                    .padding(1.dp, 1.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ) {
        Button(onClick = {
            multiplePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }, modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(1F),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffF57C00),
                contentColor = Color.White
            )) {
            Text(text = "Pick photos")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevDetailScreen() {
        RegisterScreen()
}