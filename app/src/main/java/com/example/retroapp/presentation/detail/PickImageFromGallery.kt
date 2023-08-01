package com.example.retroapp.presentation.detail

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.retroapp.R
import com.example.retroapp.data.model.Notes

@Composable
fun PickImageFromGallery(
    note: Notes,
    selectedImages: MutableState<List<Uri>>,
    viewModel: DetailViewModel
) {

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            selectedImages.value = uris
            viewModel.listUri = uris
            viewModel.onImagesChange(viewModel.listUri)
        }
    )

    var showDialog by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    var alertDialogSize by remember { mutableStateOf(IntSize.Zero) }
    var imageSize by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 5.dp, 5.dp, 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items(viewModel.listUri) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp, 120.dp)
                        .padding(1.dp, 1.dp)
                        .clickable {
                            showDialog = true
                            selectedImage = uri
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
    Spacer(modifier = Modifier.width(5.dp))
    Button(
        onClick = {
            multiplePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(1F),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue),
            contentColor = Color.White
        )
    ) {
        Text(text = "Select Image")
    }

    if (showDialog) {
        val imagePainter = rememberAsyncImagePainter(model = selectedImage)
        AlertDialog(
            modifier = Modifier.onGloballyPositioned { alertDialogSize = it.size },
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Image") },
            text = {
                Box(
                    modifier = Modifier
                        .size(300.dp, 400.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    var scale by remember { mutableStateOf(1f) }
                    var rotation by remember { mutableStateOf(0f) }
                    var offset by remember { mutableStateOf(Offset.Zero) }

                    Image(
                        painter = imagePainter,
                        contentDescription = "Image",
                        modifier = Modifier
                            .onGloballyPositioned { imageSize = it.size }
                            .fillMaxSize()
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale,
                                rotationZ = rotation,
                                translationX = offset.x,
                                translationY = offset.y
                            )
                            .pointerInput(Unit) {
                                detectTransformGestures { _, offsetChange, zoom, _ ->
                                    val newScale = scale * zoom
                                    val maxScale = 3.0f
                                    val minScale = 0.5f

                                    scale = newScale.coerceIn(minScale, maxScale)

                                    val imageWidth = imageSize.width * scale
                                    val imageHeight = imageSize.height * scale
                                    val maxX = if (imageWidth > alertDialogSize.width) (imageWidth - alertDialogSize.width) / 2 else (alertDialogSize.width - imageWidth) / 2
                                    val maxY = if (imageHeight > alertDialogSize.height) (imageHeight - alertDialogSize.height) / 2 else (alertDialogSize.height - imageHeight) / 2
                                    val minX = -maxX
                                    val minY = -maxY
                                    offset += offsetChange
                                    offset = Offset(
                                        x = offset.x.coerceIn(minX, maxX),
                                        y = offset.y.coerceIn(minY, maxY)
                                    )
                                }
                            },
                        contentScale = ContentScale.Fit
                    )
                }
            },
            confirmButton = {
                if (viewModel.listUri.contains(selectedImage)) {
                    Button(
                        onClick = {
                            selectedImage?.let { uri ->
                                viewModel.deleteImage(note.id, uri.toString()) { success ->
                                    if (success) {
                                        val updatedList = viewModel.listUri.filter { it != uri }
                                        selectedImages.value = updatedList
                                        viewModel.listUri = updatedList
                                        viewModel.onImagesChange(updatedList)
                                        showDialog = false
                                    } else {
                                        //Handle hata durumu
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Delete Image")
                    }
                }
            },
            dismissButton = { }
        )
    }
}