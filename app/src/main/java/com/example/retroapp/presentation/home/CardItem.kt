package com.example.retroapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
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
    onLongClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Card(
            modifier = Modifier
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onLongClick
                )
                .padding(8.dp)
                .fillMaxWidth()
                .border(
                    1.5.dp,
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
                            .padding(start = 6.dp, top = 2.dp, end = 6.dp)
                            .blur(10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                    )
                    when (card.type) {
                        "Teknik Karar Toplantısı" -> {
                            Image(
                                painter = painterResource(id = R.drawable.green_circle_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    //.align(Alignment.End)
                                    .padding(8.dp)
                            )
                        }

                        "Retro Toplantısı" -> {
                            Image(
                                painter = painterResource(id = R.drawable.yellow_circle_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    //.align(Alignment.End)
                                    .padding(8.dp)
                            )
                        }

                        else -> {
                            Image(
                                painter = painterResource(id = R.drawable.blue_circle_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    //.align(Alignment.End)
                                    .padding(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = card.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(6.dp)
                        .blur(10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                )

                Spacer(modifier = Modifier.size(6.dp))

                Text(
                    text = card.username,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(6.dp)
                        .blur(10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
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
                        .align(Alignment.CenterHorizontally)
                        .blur(10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
                    maxLines = 4,
                )
            }
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

private fun formatDate(timestamp: com.google.firebase.Timestamp): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}