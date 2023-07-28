package com.example.retroapp.presentation.retro.chat

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.retroapp.R
import com.example.retroapp.navigation.ROUTE_HOME
import com.example.retroapp.presentation.retro.RetroCardItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController,
) {
    val isExitMeetingDialogOpen = remember { mutableStateOf(false) }
    val selectedOption = rememberSaveable() { mutableStateOf("Select Type") }
    val comment = rememberSaveable() { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(navController,isExitMeetingDialogOpen)
        },
        bottomBar = {
            BottomBar(selectedOption,comment)
        }
    ) { contentPadding ->
        Column(modifier = Modifier
            .padding(contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
           ) {
            val retroCommentList = listOf(
                RetroComment("Good job 1", "Improvements 1"),
                RetroComment("Good job 2", "Improvements 2"),
                // ... diğer RetroComment nesneleri
            )
            LazyVerticalStaggeredGrid(modifier = Modifier.blur(radius=10.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 2.dp,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(retroCommentList.size) {
                    Log.d("goodJob",retroCommentList[it].goodJob)
                    RetroCardItem(
                        goodJob =retroCommentList[it].goodJob,
                        improvements =retroCommentList[it].improvements
                    )
                }
            }
            if(isExitMeetingDialogOpen.value){
                ExitMeetingDialog(onDismiss = { }, navController = navController )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar( navController: NavHostController,isExitMeetingDialogOpen:MutableState<Boolean>) {
    TopAppBar(
        modifier = Modifier.background(Color.White),
        title = { },
        navigationIcon = {
            IconButton(modifier = Modifier.padding(5.dp,0.dp,0.dp,15.dp),
                onClick = {
                    navController.navigate(ROUTE_HOME)

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun BottomBar(selectedOption:MutableState<String>,comment:MutableState<String>) {
      val contextForToast = LocalContext.current.applicationContext

    Scaffold(modifier = Modifier
        .padding(10.dp)
        .background(Color.White)
        .size(450.dp, 600.dp)
    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = Color.White)
                .padding(10.dp, contentPadding.calculateTopPadding(), 15.dp, bottom = 15.dp)
                .fillMaxSize()
        ) {

            Box(modifier = Modifier.fillMaxWidth(1F)) {
                Row(
                    modifier = Modifier.fillMaxWidth(1F),
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = selectedOption.value == "1",
                        onClick = { selectedOption.value = "1" },
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.blue), // Seçili durumda içeriğin rengi
                            unselectedColor = Color.Black // Seçili olmadığında içeriğin rengi
                        )
                    )
                    Text(stringResource(id = R.string.iyi_giden), modifier = Modifier.align(CenterVertically), fontSize = 14.sp)
                    // Spacer(modifier = Modifier.width(15.dp))
                    RadioButton(
                        selected = selectedOption.value == "2",
                        onClick = { selectedOption.value = "2" },
                        colors = androidx.compose.material3.RadioButtonDefaults.colors(
                            selectedColor = colorResource(id = R.color.blue), // Seçili durumda içeriğin rengi
                            unselectedColor = Color.Black // Seçili olmadığında içeriğin rengi
                        )
                    )
                    Text(
                        stringResource(id = R.string.gelistirilmesi_gereken),
                        modifier = Modifier.align(CenterVertically), fontSize = 14.sp
                    )
                }
            }

            Row() {
                OutlinedTextField(

                    value = comment.value,
                    onValueChange = { comment.value = it },
                    label = { Text("Comment", color = Color.Black, fontSize = 14.sp) },
                    modifier = Modifier
                        .padding(1.dp, 1.dp, 1.dp, 5.dp),
                    maxLines = 6,
                    colors= TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(id = R.color.blue), // Odaklanıldığında kenar çizgisi rengi
                        unfocusedBorderColor = Color.Black, // Odak dışında kenar çizgisi rengi
                        focusedLabelColor = Color.Black, // Odaklanıldığında etiket rengi
                        unfocusedLabelColor = Color.Black // Odak dışında etiket rengi
                    )
                )

                Box(
                    modifier = Modifier
                        .size(57.dp, 62.dp)
                        .align(Bottom)
                        .padding(start = 2.dp, bottom = 5.dp)
                        .background(
                            colorResource(id = R.color.blue),
                            shape = RoundedCornerShape(5.dp),
                        ),
                ) {
                    IconButton( modifier = Modifier.align(Center),
                        onClick = {

                        }) {
                        Icon(
                            tint = Color.White,
                            painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                            contentDescription = "Add Comment Icon",)
                    }
                }

            }
        }
    }

}

@Preview
@Composable
fun Review() {
    val chatViewModel = ChatViewModel()

    // NavController oluşturun, burada uygun bir şekilde başlatılmalı
    val navController = rememberNavController()

    // ChatScreen'e gerekli parametreleri geçin
    ChatScreen(chatViewModel = chatViewModel, navController = navController)
}
data class RetroComment(
    val goodJob: String,
    val improvements: String
)