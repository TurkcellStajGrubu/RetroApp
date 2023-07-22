package com.example.retroapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.retroapp.R
import com.example.retroapp.data.model.Notes
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardItem(
    card: Notes,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(8.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .border(
                2.dp,
                Color(R.color.white_f10),
                RoundedCornerShape(5.dp)
            ),
        colors = CardDefaults.cardColors(colorResource(getColorForCardType(card.type)))
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = formatDate(card.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(7.dp, 2.dp)
                )
                when (card.type) {
                    "Teknik Karar Toplantısı" -> GetImage(typeIcon = R.drawable.green_circle_icon)
                    "Retro Toplantısı" -> GetImage(typeIcon = R.drawable.yellow_circle_icon)
                    else -> GetImage(R.drawable.blue_circle_icon)
                }
            }

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = card.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(6.dp)
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = card.username,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(6.dp),
                maxLines = 4
            )

            Divider(
                modifier = Modifier.height(2.dp),
                color = Color(R.color.white_f10)
            )

            Text(
                text = card.type,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 4
            )
        }
    }
}
private fun getColorForCardType(type: String): Int {
    return when (type) {
        "Teknik Karar Toplantısı" -> R.color.white_f2
        "Retro Toplantısı" -> R.color.white_f5
        else -> R.color.white_f8
    }
}
@Composable
private fun GetImage(typeIcon:Int){
    Image(
        painter = painterResource(id = typeIcon),
        contentDescription = null,
        modifier = Modifier
            .padding(8.dp)
    )
}
private fun formatDate(timestamp: com.google.firebase.Timestamp): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}