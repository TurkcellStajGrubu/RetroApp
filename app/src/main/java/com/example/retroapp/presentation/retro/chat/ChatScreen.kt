package com.example.retroapp.presentation.retro.chat

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.retroapp.R
import com.example.retroapp.data.model.Notes
import com.example.retroapp.navigation.ROUTE_HOME
import com.google.firebase.Timestamp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel,
    navController: NavHostController,
) {
    val note = remember { mutableStateOf(Notes()) }
    val isDeleteDialogOpen = remember { mutableStateOf(false) }
    val isAdmin = remember { mutableStateOf(false) }
    val adminConfirm = remember { mutableStateOf(false) }
    //Log.d("admin",chatViewModel.meetingAdminId.value.toString() )
    val adminId = chatViewModel.meetingAdminId.value // düzenlenicek
    //Log.d("user",chatViewModel.getUserId)
    if(adminId==chatViewModel.getUserId)  isAdmin.value=true

    LaunchedEffect(chatViewModel.remainingTime.value, isAdmin) {
        if (chatViewModel.remainingTime.value == "00:00") {
            if (isAdmin.value) {
                adminConfirm.value = true
            } else {
                navController.navigate(ROUTE_HOME)
                Log.d("navigate", "navigate")
            }
        }
    }

    LaunchedEffect(chatViewModel.resetEvent) {
        snapshotFlow { chatViewModel.resetEvent.value }
            .collect { reset ->
                if (reset) {
                    adminConfirm.value = false
                    chatViewModel.resetEvent.value = false
                }
            }
    }

    Scaffold(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.White),

        topBar = {
            TopBar(
                navController,
                meetingTitle = chatViewModel.meetingTitle.value ?: "",
                adminName = chatViewModel.adminName.value ?: "",
                remainingTime = chatViewModel.remainingTime.value,
                chatViewModel = chatViewModel,
                isAdmin = isAdmin
            )
        },
        bottomBar = {
            if (adminConfirm.value)
                AddBottomBar(viewModel = chatViewModel, navController = navController)
            else
                BottomBar(chatViewModel, adminConfirm, navController)

        },

        ) { contentPadding ->
        Column(modifier = Modifier
            .padding(contentPadding)
            .padding(bottom = 5.dp)) {
            if (adminConfirm.value) {

                        LazyVerticalStaggeredGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = StaggeredGridCells.Fixed(2),
                            verticalItemSpacing = 2.dp,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                                chatViewModel.getRetro(chatViewModel.activeRetroId.value).let { it ->
                                items(it.notes, key = { note -> note.id }) { card ->
                                ChatCardItem(card.description, onLongClick = {
                                    isDeleteDialogOpen.value = true; note.value = card
                                })
                            }
                        }
                    }
                }

           else {

                LazyVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxHeight(),
                    columns = StaggeredGridCells.Fixed(2), verticalItemSpacing = 2.dp,
                    horizontalArrangement = Arrangement.spacedBy(2.dp) ){
                    chatViewModel.activeRetro.value?.let {
                        items(it.notes){ card ->
                            ChatCardItem("", onLongClick = {})
                        }
                    }
                }
            }
            if (isDeleteDialogOpen.value) {
                AlertDialog(
                    onDismissRequest = {
                        isDeleteDialogOpen.value = false
                    },
                    title = {
                        Text(text = stringResource(id = R.string.delete))
                    },
                    text = {
                        Text(text =  stringResource(id = R.string.want_delete))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                Log.d("note", note.value.toString())
                                chatViewModel.deleteNotesFromRetro(chatViewModel.activeRetroId.value, note.value)
                                isDeleteDialogOpen.value = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(text =stringResource(id = R.string.delete))
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                isDeleteDialogOpen.value = false
                            }
                        ) {
                            Text(text =  stringResource(id = R.string.cancel))
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, adminName: String, meetingTitle: String, remainingTime: String,isAdmin:MutableState<Boolean>, chatViewModel : ChatViewModel) {
    val mDisplayMenu = remember { mutableStateOf(false) }
    TopAppBar(
        modifier = Modifier.background(Color.White),
        title = {
            Text(text = meetingTitle, fontSize = 16.sp)
        },
        //Kalkabilir settings kısmında toplantı sonlandır var
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .background(colorResource(id = R.color.wred), shape = RoundedCornerShape(5.dp)),
                onClick = {
                    navController.navigate(ROUTE_HOME)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colorResource(id = R.color.dred)
                )
            }
        },
        actions = {
            Text(
                text = remainingTime,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(end = 75.dp)
                    .align(CenterVertically),
                color = Color.Black
            )
            Text(
                text = adminName,
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 10.dp),
                color = Color.Black
            )
            IconButton(onClick = { mDisplayMenu.value = !mDisplayMenu.value }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
           if(isAdmin.value)
               AdminDropdownItem(mDisplayMenu = mDisplayMenu, navController = navController, chatViewModel = chatViewModel)
            else
               UserDropdownItem(mDisplayMenu = mDisplayMenu, navController =navController,chatViewModel= chatViewModel )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable

fun BottomBar(viewModel: ChatViewModel, adminConfirm: MutableState<Boolean>, navController: NavHostController) {
    val selectedOption = rememberSaveable() { mutableStateOf("") }
    val comment = rememberSaveable() { mutableStateOf("") }
    val contextForToast = LocalContext.current.applicationContext


    Scaffold(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.White)
            .size(450.dp, 150.dp)


    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = Color.White)
                .padding(1.dp, contentPadding.calculateTopPadding(), 15.dp, bottom = 15.dp)
                .fillMaxSize()

        ) {
                Box(modifier = Modifier.fillMaxWidth(1F)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(1F),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            selected = selectedOption.value == "İyi Giden",
                            onClick = { selectedOption.value = "İyi Giden" },
                            colors = androidx.compose.material3.RadioButtonDefaults.colors(
                                selectedColor = colorResource(id = R.color.blue), // Seçili durumda içeriğin rengi
                                unselectedColor = Color.Black // Seçili olmadığında içeriğin rengi
                            )
                        )
                        Text(
                            stringResource(id = R.string.iyi_giden),
                            modifier = Modifier.align(CenterVertically),
                            fontSize = 14.sp
                        )
                        RadioButton(
                            selected = selectedOption.value == "Geliştirilmesi Gereken",
                            onClick = { selectedOption.value = "Geliştirilmesi Gereken" },
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

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(2f)
                            .align(CenterVertically)
                            .padding(1.dp, 0.dp, 1.dp, 5.dp)
                    ){
                        OutlinedTextField(
                            value = comment.value,
                            onValueChange = { comment.value = it },
                            label = { Text("Comment", color = Color.Black, fontSize = 14.sp) },
                            modifier = Modifier
                                .align(CenterEnd)
                                .padding(1.dp, 0.dp, 1.dp, 5.dp)
                            //    .size(width = 150.dp, height = 62.dp)
                            ,
                            maxLines = 6,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.blue), // Odaklanıldığında kenar çizgisi rengi
                                unfocusedBorderColor = Color.Black, // Odak dışında kenar çizgisi rengi
                                focusedLabelColor = Color.Black, // Odaklanıldığında etiket rengi
                                unfocusedLabelColor = Color.Black // Odak dışında etiket rengi
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(0.5f)
                            .align(CenterVertically)
                            .padding(1.dp, 0.dp, 1.dp, 5.dp)
                            .background(
                                colorResource(id = R.color.blue),
                                shape = RoundedCornerShape(5.dp),
                            ),

                        ) {
                        IconButton(modifier = Modifier
                            .align(Center)
                            ,
                            onClick = {
                                if (selectedOption.value.isEmpty()) {
                                    Toast.makeText(
                                        contextForToast,
                                        "Please select note type",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    if (comment.value.isEmpty()) {
                                        Toast.makeText(
                                            contextForToast,
                                            "Comment cannot be empty",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        val note = viewModel.activeRetro.value?.let {
                                            Notes(
                                                "",
                                                it.admin,
                                                listOf(),
                                                "",
                                                "${viewModel.meetingTitle.value} & ${selectedOption.value}",
                                                "${selectedOption.value}: ${comment.value}",
                                                Timestamp.now(),
                                                "Retro Toplantısı"
                                            )
                                        }
                                        note?.let {
                                            viewModel.addNotesToRetro(
                                                viewModel.activeRetro.value?.id.toString(), it
                                            )
                                        }
                                        Toast.makeText(contextForToast, "Note succesfuly added", Toast.LENGTH_LONG).show()
                                        comment.value = ""
                                    }
                                }
                            }) {
                            Icon(
                                tint = Color.White,
                                painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                                contentDescription = "Add Comment Icon"
                            )
                        }
                    }

                }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBottomBar(viewModel: ChatViewModel,navController: NavHostController){
    Scaffold(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.White)
            .size(450.dp, 70.dp)


    ) { contentPadding ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = Color.White)
                .padding(1.dp, contentPadding.calculateTopPadding(), 15.dp, bottom = 15.dp)
                .fillMaxSize()

        ) {
            Button(
                modifier = Modifier
                    .size(200.dp, 60.dp)
                    .padding(10.dp, 5.dp, 5.dp, 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.blue),
                    contentColor = Color.White
                ),onClick = {
                    viewModel.addConfirmedNotes(viewModel.activeRetroId.value)
                    navController.navigate(ROUTE_HOME)
                }
            ) {
                Text(text = "Kaydet")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun  ChatScreenPreview(){
    ChatScreen(chatViewModel = viewModel(), navController = rememberNavController( ))
}