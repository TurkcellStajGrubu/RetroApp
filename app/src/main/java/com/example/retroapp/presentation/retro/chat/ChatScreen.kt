package com.example.retroapp.presentation.retro.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retroapp.R

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    navController: NavController,
    onFabClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            FloatingActionButton(
                onClick = { onFabClick() },
                modifier = Modifier
                    .padding(bottom = 15.dp, end = 15.dp)
                    .align(Alignment.End),
                containerColor = colorResource(id = R.color.blue)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.message_vector_asset),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}