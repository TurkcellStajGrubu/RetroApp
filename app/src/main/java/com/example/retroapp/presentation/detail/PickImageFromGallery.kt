package com.example.retroapp.presentation.detail

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.retroapp.R

@Composable
fun PickImageFromGallery(selectedImages: MutableState<List<Uri>>,viewModel: DetailViewModel) {
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            selectedImages.value = uris
            viewModel.listUri = uris
            viewModel.onImagesChange(viewModel.listUri)
        }
    )
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 5.dp, 5.dp, 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items(selectedImages.value) { uri ->
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
    ){
        Text(text = "Photo")}
}