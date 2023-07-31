package com.example.retroapp.presentation.retro

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retroapp.R

@Composable
fun RetroCardItem(goodJob: String, improvements: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .border(
                1.5.dp,
                Color(R.color.white_f10),
                RoundedCornerShape(5.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "İyi Giden İşler",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = goodJob,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Geliştirilmesi Gereken İşler",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = improvements,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                color = Color.Black,
            )
        }
    }
}

@Preview
@Composable
fun PreviewCard() {
    RetroCardItem(
        goodJob = "İyi giden işlerin açıklaması yer alacak.",
        improvements = "Geliştirilmesi gereken işlerin açıklaması yer alacak."
    )
}