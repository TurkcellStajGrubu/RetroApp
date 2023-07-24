package com.example.retroapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.retroapp.R
import com.example.retroapp.data.Resource
import com.example.retroapp.data.model.Notes
import com.example.retroapp.presentation.auth.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    homeViewModel: HomeViewModel,
    onCardClick: (Notes) -> Unit,
    onFabClick: () -> Unit,
    navController: NavHostController,
) {


    val noteId = remember { mutableStateOf("") }
    val mDisplayMenu = remember { mutableStateOf(false) }
    val visible = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }
    val filterType = remember { mutableStateOf("") }
    val isDeleteDialogOpen = remember { mutableStateOf(false) }
    val notesState by homeViewModel.getFilteredNotes(searchText.value, filterType.value).collectAsState(null)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFabClick() },
                modifier = Modifier.padding(bottom = 72.dp),
                containerColor = colorResource(id = R.color.blue)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        topBar = {
            TopAppBar(
                navigationIcon = {},
                actions = {
                        OutlinedTextField(
                            value = searchText.value,
                            onValueChange = { searchText.value = it },
                            label = { Text(stringResource(id = R.string.search), color = Color.Black, modifier = Modifier.align(CenterVertically)) },
                            modifier = Modifier
                                .padding(1.dp)
                                .size(300.dp, 60.dp)
                        )
                    IconButton(onClick = { visible.value = !visible.value }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp, 30.dp)
                        )
                    }
                    IconButton(onClick = { mDisplayMenu.value = !mDisplayMenu.value }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp, 30.dp)
                        )
                    }
                    DropdownItem(
                        mDisplayMenu = mDisplayMenu,
                        filterType = filterType,
                        authViewModel = authViewModel,
                        navController = navController
                    )
                },
                title = {}
            )
        }
    ) { contentPadding ->
        Column(modifier = Modifier
            .padding(contentPadding)
            .padding(bottom = 72.dp)) {
            when (notesState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is Resource.Success -> {
                    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2), verticalItemSpacing = 2.dp,
                        horizontalArrangement = Arrangement.spacedBy(2.dp) ){
                        items((notesState as Resource.Success<List<Notes>>).result){
                                card ->
                            CardItem(
                                card = card,
                                onClick = { onCardClick(card) },
                                onLongClick = {isDeleteDialogOpen.value = true; noteId.value = card.id;}
                            )
                        }

                    }
                }
                is Resource.Failure -> {
                    Text(
                        text = stringResource(id = R.string.error_loading_data),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {}
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
                            Text(text = stringResource(id = R.string.want_delete))
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    homeViewModel.deleteNote(noteId.value, onComplete = {})
                                    isDeleteDialogOpen.value = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                            ) {
                                Text(text =stringResource(id = R.string.delete))
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { isDeleteDialogOpen.value = false }
                            ) {
                                Text(text = stringResource(id = R.string.cancel))
                            }
                        }
                    )
            }
        }
    }
}

