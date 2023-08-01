package com.example.retroapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.example.retroapp.presentation.ui.theme.DarkBlue
import com.example.retroapp.presentation.ui.theme.LightGray
import com.example.retroapp.presentation.ui.theme.Yellow

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


    val notesState by homeViewModel.getFilteredNotes(searchText.value, filterType.value)
        .collectAsState(null)

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
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(1F)
                                .padding(6.dp, 2.dp)
                        ) {
                            TextField(
                                value = searchText.value,
                                onValueChange = { searchText.value = it },
                                label = {
                                    Text(
                                        stringResource(id = R.string.search),
                                        color = Color.Gray,
                                        modifier = Modifier.align(CenterHorizontally)
                                    )
                                },
                                modifier = Modifier
                                    .padding(1.dp)
                                    .size(280.dp, 55.dp).background(Color.White,shape= RoundedCornerShape(size = 40.dp)),
                                colors = TextFieldDefaults.outlinedTextFieldColors( textColor = Color.Black, placeholderColor = DarkBlue, cursorColor = DarkBlue, focusedBorderColor = DarkBlue, unfocusedBorderColor =DarkBlue)

                            )
                        }
                        Row(
                            verticalAlignment = CenterVertically,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            IconButton(onClick = { visible.value = !visible.value }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.White
                                )
                            }
                            IconButton(onClick = { mDisplayMenu.value = !mDisplayMenu.value }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = Color.White
                                )
                            }
                            DropdownItem(
                                mDisplayMenu = mDisplayMenu,
                                filterType = filterType,
                                authViewModel = authViewModel,
                                navController = navController
                            )
                        }
                    }
                },
                title = {
                },colors = TopAppBarDefaults.largeTopAppBarColors(DarkBlue)
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .padding(bottom = 72.dp)
        ) {
            when (notesState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(CenterHorizontally))
                }

                is Resource.Success -> {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2), verticalItemSpacing = 2.dp,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items((notesState as Resource.Success<List<Notes>>).result) { card ->
                            CardItem(
                                card = card,
                                onClick = { onCardClick(card) },
                                onLongClick = {
                                    isDeleteDialogOpen.value = true; noteId.value = card.id;
                                }
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
                AlertDialog(modifier = Modifier.background(color=DarkBlue,shape = RoundedCornerShape(size = 40.dp)),
                    onDismissRequest = {
                        isDeleteDialogOpen.value = false
                    },
                    title = {
                        Text(text = stringResource(id = R.string.delete),color=DarkBlue)
                    },
                    text = {
                        Text(text = stringResource(id = R.string.want_delete))
                    },
                    confirmButton = {
                        Button(modifier = Modifier.size(160.dp,40.dp), colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                            onClick = {
                                homeViewModel.deleteNote(noteId.value, onComplete = {})
                                isDeleteDialogOpen.value = false
                            },
                        ) {
                            Text(text = stringResource(id = R.string.delete), color = DarkBlue)
                        }
                    },
                    dismissButton = {
                        Button(modifier = Modifier .border(1.dp, Yellow, shape = RoundedCornerShape(size = 40.dp)) .size(100.dp, 38.dp), colors = ButtonDefaults.buttonColors( containerColor = Color.Transparent ),
                            onClick = {
                                isDeleteDialogOpen.value = false
                            }
                        ) {
                            Text(text = stringResource(id = R.string.cancel),color=DarkBlue)
                        }
                    }
                )
            }
        }
    }
}
