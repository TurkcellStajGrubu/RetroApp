package com.example.retroapp.presentation.retro.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.retroapp.R
import com.example.retroapp.navigation.ROUTE_HOME
import com.example.retroapp.presentation.ui.theme.Yellow

@Composable
fun ExitMeetingDialog(
    onDismiss: () -> Unit,
    chatViewModel: ChatViewModel,
    navController: NavHostController,
    dialogText: String,
    isAdmin: Boolean
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.alertdialog_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (isAdmin) {
                    Text(
                        text = stringResource(id = R.string.toplantiyi_sonlandir),
                        color = Color.White,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.toplantidan_ayril),
                        color = Color.White,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(text = dialogText, color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier
                            .border(1.dp, Yellow, shape = RoundedCornerShape(size = 40.dp))
                            .size(115.dp, 40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        onClick = onDismiss
                    ) {
                        Text(
                            text = stringResource(id = R.string.hayir),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Button(
                        modifier = Modifier.size(140.dp, 40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                        onClick = {
                            if (isAdmin) {
                                onDismiss()
                                chatViewModel.updateRetroTime(0)
                            } else {
                                onDismiss()
                                navController.navigate(ROUTE_HOME)
                            }
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.evet),
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}



