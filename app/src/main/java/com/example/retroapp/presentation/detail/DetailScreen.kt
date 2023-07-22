package com.example.retroapp.presentation.detail

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.retroapp.R
import com.example.retroapp.data.model.Notes
import com.example.retroapp.navigation.ROUTE_HOME
import com.google.firebase.Timestamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel?,
    isDetail:Boolean?, navController: NavHostController,
    noteId: String
) {
    val note = remember { mutableStateOf(Notes()) }
    if (isDetail == true){
        note.value = viewModel?.getNote(noteId)!!
    }
    val activity = LocalContext.current as? ComponentActivity
    val parentOptions = listOf("Teknik Karar Toplantısı", "Retro Toplantısı", "Cluster Toplantısı")
    val selectedOption =
        remember { mutableStateOf(parentOptions[0]) }
    val title = rememberSaveable() { mutableStateOf("") }
    val detail = remember { mutableStateOf("") }
    val selectedImageUris = remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
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
                    title.value = note.value.title
                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { title.value = it; Log.d("13424543", it)},
                        label = { Text("Title", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp)
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    selectedOption.value = viewModel?.getNote(noteId)!!.type
                    DisplaySpinner(selectedOption, parentOptions)

                    OutlinedTextField(
                        value = note.value.description,
                        onValueChange = { detail.value = it },
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
                    DisplaySpinner(selectedOption, parentOptions)

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
                if (isDetail == true){
                    val selectedImages = arrayListOf<Uri>()
                    note.value.images?.forEach {
                        selectedImages.add(Uri.parse(it))
                    }
                    selectedImageUris.value = selectedImages
                    Log.d("select", selectedImageUris.toString())
                    Log.d("select123", selectedImages.toString())
                    PickImageFromGallery(selectedImageUris)
                    Button(
                        onClick = {
                                val images = arrayListOf<String>()
                                selectedImageUris.value.forEach { uri -> images.add(uri.toString()) }
                            Log.d("title", title.value)
                                viewModel?.updateNote(
                                    title.value,
                                    detail.value,
                                    note.value.id,
                                    images,
                                    selectedOption.value,
                                    onResult = {
                                        navController.navigate(ROUTE_HOME)
                                        Toast.makeText(
                                            contextForToast,
                                            "Note succesfully updated",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    })

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
                    PickImageFromGallery(selectedImageUris)
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
                                val images = arrayListOf<String>()
                                selectedImageUris.value.forEach { uri -> images.add(uri.toString()) }
                                viewModel?.addNote(
                                    title.value,
                                    detail.value,
                                    images,
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
                            Text(text = stringResource(id = R.string.update))
                        }
                        AnimatedVisibility(visible = !isDetail) {
                            Text(text =stringResource(id = R.string.add_screen))
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
@Preview(showSystemUi = true)
@Composable
fun PrevDetailScreen() {
    DetailScreen(null, isDetail = null, rememberNavController(), "")
}