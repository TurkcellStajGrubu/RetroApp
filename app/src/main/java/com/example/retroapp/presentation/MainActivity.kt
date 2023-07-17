package com.example.retroapp.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.retroapp.R
import com.example.retroapp.presentation.detail.RegisterScreen
import com.example.retroapp.presentation.home.HomeScreen
import com.example.retroapp.presentation.retro.RetroScreen
import com.example.retroapp.presentation.ui.theme.RetroAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetroAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation("Android")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(name: String, modifier: Modifier = Modifier) {
    val items = listOf("HomeScreen", "RetroScreen")
    val selectedpage = remember { mutableStateOf(0)}
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Home")})
        },
        content = {
            if (selectedpage.value == 0) {
                HomeScreen()
            }

            if (selectedpage.value == 1) {
                RetroScreen()
            }
        },
        bottomBar = {
            NavigationBar() {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedpage .value == index,
                        onClick = {selectedpage.value = index},
                        label = { Text(text = item)},
                        icon = {
                            when(item) {
                                "HomeScreen" -> Icon(painter = painterResource(id = R.drawable.ic_home_icon),
                                contentDescription = "")
                                "RetroScreen" -> Icon(painter = painterResource(id = R.drawable.ic_retro_icon),
                                    contentDescription = "")
                            }
                        }

                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RetroAppTheme {
        Navigation("Android")
    }
}