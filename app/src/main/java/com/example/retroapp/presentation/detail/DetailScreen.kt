package com.example.retroapp.presentation.detail

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.retroapp.R
import com.example.retroapp.navigation.ROUTE_HOME
import com.google.firebase.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    isDetail:Boolean?, navController: NavHostController,
    noteId: String
) {
    LaunchedEffect(key1 = true){
        if (isDetail == true){
            viewModel.getNote(noteId)
        } else {
            viewModel.listUri = emptyList()
        }
    }
    val activity = LocalContext.current as? ComponentActivity
    val parentOptions = listOf("Teknik Karar Toplantısı", "Retro Toplantısı", "Cluster Toplantısı")
    val selectedOption = rememberSaveable() { mutableStateOf(parentOptions[0]) } //Seçilen toplantı türünü tutuyor
    val title = rememberSaveable() { mutableStateOf("") }
    val detail = rememberSaveable() { mutableStateOf("") }
    val selectedImageUris = rememberSaveable() { mutableStateOf<List<Uri>>(emptyList()) }
    val contextForToast = LocalContext.current.applicationContext

    Scaffold(
        topBar = {
            TopBar(isDetail = isDetail ?: false, onBackClick = { navController.popBackStack() })
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(20.dp, 5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = CenterHorizontally
            ) {
                if (isDetail == true){
                    OutlinedTextField(
                        value = viewModel.note.title,
                        onValueChange = { viewModel.onTitleChange(it) },
                        label = { Text("Title", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    selectedOption.value = viewModel.note.type
                    DisplaySpinner(selectedOption, parentOptions, viewModel)
                    OutlinedTextField(
                        value = viewModel.note.description,
                        onValueChange = { viewModel.onDetailChange(it) },
                        label = { Text("Detail", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 6,
                    )
                } else{
                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text("Title", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    DisplaySpinner(selectedOption, parentOptions, viewModel)
                    OutlinedTextField(
                        value = detail.value,
                        onValueChange = { detail.value = it },
                        label = { Text("Detail", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 6,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .padding(0.5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = CenterHorizontally
            ) {
                PickImageFromGallery(selectedImageUris ,viewModel)
                if (isDetail == true){
                    Button(
                        onClick = {
                                viewModel.updateNote(
                                    viewModel.note.title,
                                    viewModel.note.description,
                                    viewModel.note.id,
                                    viewModel.listUri,
                                    selectedOption.value
                                ) {
                                    navController.navigate(ROUTE_HOME)
                                    Toast.makeText(
                                        contextForToast,
                                        "Note succesfully updated",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(1F),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.blue),
                            contentColor = Color.White
                        )
                    ) {
                        AnimatedVisibility(visible = isDetail) {
                            Text(text = "Update")
                        }
                        AnimatedVisibility(visible = !isDetail) {
                            Text(text = "Add")
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                } else{
                    Button(
                        onClick = {
                            if (title.value.isEmpty()) {
                                Toast.makeText(
                                    contextForToast,
                                    "Title cannot be empty",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else if (detail.value.isEmpty()) {
                                Toast.makeText(
                                    contextForToast,
                                    "Detail cannot be empty",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                viewModel.addNote(
                                    title.value,
                                    detail.value,
                                    selectedImageUris.value,
                                    Timestamp.now(),
                                    selectedOption.value,
                                    onComplete = {
                                        navController.navigate(ROUTE_HOME)
                                        Toast.makeText(
                                            contextForToast,
                                            "Note succesfully added",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    })
                            }
                        },
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(1F),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.blue),
                            contentColor = Color.White
                        )
                    ) {
                        AnimatedVisibility(visible = isDetail!!) {
                            Text(text = "Update")
                        }
                        AnimatedVisibility(visible = !isDetail) {
                            Text(text = "Add")
                        }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
        DisposableEffect(Unit) {
            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigate(ROUTE_HOME)

                }
            }

            activity?.onBackPressedDispatcher?.addCallback(callback)

            onDispose {
                callback.remove()
            }
        }
    }
}

@Composable
fun ClickableDetail(
    message: String,
) {
    val uriHandler = LocalUriHandler.current
    val styledMessage = textFormatter(
        text = message
    )
    ClickableText(
        text = styledMessage,
        style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
        modifier = Modifier.padding(16.dp),
        onClick = {
            styledMessage
                .getStringAnnotations(start = it, end = it)
                .firstOrNull()
                ?.let { annotation ->
                    when (annotation.tag) {
                        SymbolAnnotationType.LINK.name -> uriHandler.openUri(annotation.item)
                        else -> Unit
                    }
                }
        })
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(isDetail: Boolean, onBackClick: () -> Unit) {
    var textRes = R.string.add_screen
    if (isDetail) textRes = R.string.detail_screen

    TopAppBar(
        modifier = Modifier.background(Color.White),
        title = {
            Text(
                text = stringResource(textRes)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackClick()
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
