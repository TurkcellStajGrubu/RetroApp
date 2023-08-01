package com.example.retroapp.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retroapp.presentation.ui.theme.DarkBlue
import com.example.retroapp.presentation.ui.theme.LightGray


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySpinner(
    selectedOption: MutableState<String>,
    parentOptions: List<String>,
    viewModel: DetailViewModel
) {
    val expandedState = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(1F)
            .padding(1.dp)
            .clickable(onClick = { expandedState.value = true })
            .background(
                LightGray,
                RectangleShape
            )
    ) {
        TextField(
            value = selectedOption.value,
            modifier = Modifier
                .fillMaxWidth(1F),
            onValueChange = {},
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Drop-down",
                    modifier = Modifier.clickable {
                        expandedState.value = !expandedState.value
                    }
                )
            },
            readOnly = true,
            textStyle = TextStyle.Default.copy(fontSize = 16.sp),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        DropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = { expandedState.value = false },
            Modifier.background(LightGray)
        ) {
            parentOptions.forEach { option ->
                DropdownMenuItem(modifier = Modifier
                    .fillMaxWidth(1F),
                    onClick = {
                        viewModel.onTypeChange(option)
                        selectedOption.value = viewModel.note.type;
                        selectedOption.value = option
                        expandedState.value = false
                    }, text = { Text(text = option, fontSize = 16.sp, style = TextStyle.Default) })
                Divider(color= DarkBlue)
            }
        }

    }
}