package com.example.retroapp.presentation.auth

import android.content.res.Configuration
import android.graphics.Paint.Style
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retroapp.presentation.ui.theme.RetroAppTheme
import com.example.retroapp.presentation.ui.theme.spacing
import com.example.retroapp.R
import com.example.retroapp.presentation.ui.theme.MidnightBlue

@Composable
fun AuthHeader() {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val spacing = MaterialTheme.spacing

        Image(
            modifier = Modifier
                .size(600.dp, 150.dp),
            painter = painterResource(id = R.drawable.turkcell_logo),
            contentDescription = stringResource(id = R.string.header_title)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = spacing.medium),
            text = stringResource(id = R.string.header_title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = MidnightBlue
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AppHeaderLight() {
    RetroAppTheme() {
        AuthHeader()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppHeaderDark() {
    RetroAppTheme() {
        AuthHeader()
    }
}