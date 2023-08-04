package com.example.retroapp.presentation.auth

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.retroapp.R
import com.example.retroapp.data.Resource
import com.example.retroapp.navigation.ROUTE_HOME
import com.example.retroapp.navigation.ROUTE_LOGIN
import com.example.retroapp.navigation.ROUTE_SIGNUP
import com.example.retroapp.presentation.ui.theme.DarkBlue
import com.example.retroapp.presentation.ui.theme.LightBlue
import com.example.retroapp.presentation.ui.theme.LightGray
import com.example.retroapp.presentation.ui.theme.RetroAppTheme
import com.example.retroapp.presentation.ui.theme.Yellow
import com.example.retroapp.presentation.ui.theme.spacing
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: AuthViewModel?, navController: NavController) {
    val email = remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val isForgotPasswordDialogOpen = remember { mutableStateOf(false) }
    val emailDialogOpen = remember { mutableStateOf(false) }

    val loginFlow = viewModel?.loginFlow?.collectAsState()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        val (refHeader, refEmail, refPassword, refButtonLogin, refTextSignup, refLoader, refForgotPassword) = createRefs()
        val spacing = MaterialTheme.spacing

        Box(
            modifier = Modifier
                .constrainAs(refHeader) {
                    top.linkTo(parent.top, spacing.extraLarge)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .wrapContentSize()
        ) {
            AuthHeader()
        }


        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            label = {
                Text(text = stringResource(id = R.string.email))
            },
            modifier = Modifier
                .constrainAs(refEmail) {
                    top.linkTo(refHeader.bottom, spacing.extraLarge)
                    start.linkTo(parent.start, spacing.large)
                    end.linkTo(parent.end, spacing.large)
                    width = Dimension.fillToConstraints
                }
                .background(LightGray),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                placeholderColor = Color.Gray,
                cursorColor = DarkBlue,
                focusedBorderColor = DarkBlue,
                unfocusedBorderColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = stringResource(id = R.string.password))
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .constrainAs(refPassword) {
                    top.linkTo(refEmail.bottom, spacing.medium)
                    start.linkTo(parent.start, spacing.large)
                    end.linkTo(parent.end, spacing.large)
                    width = Dimension.fillToConstraints
                }
                .background(LightGray),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Black,
                placeholderColor = Color.Gray,
                cursorColor = DarkBlue,
                focusedBorderColor = DarkBlue,
                unfocusedBorderColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisibility) "hide password" else "show password"
                    )
                }
            }
        )

        Text(
            modifier = Modifier
                .constrainAs(refForgotPassword) {
                    top.linkTo(refPassword.bottom, spacing.medium)
                    start.linkTo(refPassword.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .clickable {
                    isForgotPasswordDialogOpen.value = true
                },
            text = stringResource(id = R.string.forgot_password),
            style = MaterialTheme.typography.bodyLarge,
            color = DarkBlue
        )

        Button(
            onClick = {
                viewModel?.login(email.value, password)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Yellow),
            modifier = Modifier
                .constrainAs(refButtonLogin) {
                    top.linkTo(refForgotPassword.bottom, spacing.large)
                    start.linkTo(parent.start, spacing.extraLarge)
                    end.linkTo(parent.end, spacing.extraLarge)
                    width = Dimension.fillToConstraints
                },


        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.titleMedium,
                color= Color.Black
            )
        }


        Text(
            modifier = Modifier
                .constrainAs(refTextSignup) {
                    top.linkTo(refButtonLogin.bottom, spacing.medium)
                    start.linkTo(parent.start, spacing.extraLarge)
                    end.linkTo(parent.end, spacing.extraLarge)
                }
                .clickable {
                    navController.navigate(ROUTE_SIGNUP) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }
                },
            text = stringResource(id = R.string.dont_have_account),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = DarkBlue
        )

        if (isForgotPasswordDialogOpen.value) {
        CustomAlertDialog(
            email = email,
            isForgotPasswordDialogOpen = isForgotPasswordDialogOpen,
            emailDialogOpen = emailDialogOpen)
        }

        val context = LocalContext.current

        loginFlow?.value?.let {
            when (it) {
                is Resource.Failure -> {
                    if (!it.hasBeenHandled) {
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_SHORT).show()
                        it.hasBeenHandled = true
                    }
                }

                Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.constrainAs(refLoader) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })
                }

                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(ROUTE_HOME) {
                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAlertDialog(email:MutableState<String>,isForgotPasswordDialogOpen:MutableState<Boolean>,emailDialogOpen:MutableState<Boolean>) {
    if (!emailDialogOpen.value) {
        Dialog(
            onDismissRequest = { isForgotPasswordDialogOpen.value = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ),
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
                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        color = Color.White,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Parolanızı sıfırlamak için lütfen e-posta adresinizi girin",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(modifier = Modifier
                            .border(1.dp, Yellow, shape = RoundedCornerShape(size = 40.dp))
                            .size(115.dp, 40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            onClick = {
                                isForgotPasswordDialogOpen.value = false
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Button(
                            modifier = Modifier.size(140.dp, 40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                            onClick = {
                                emailDialogOpen.value = true
                            },
                        ) {
                            Text(
                                text = stringResource(id = R.string.reset),
                                color = Color.Black,
                                fontSize = 16.sp, textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
    else{
        Dialog(
            onDismissRequest = { isForgotPasswordDialogOpen.value = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
            ),
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
                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        color = Color.White,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    TextField(
                        modifier=Modifier.background(LightBlue),colors = TextFieldDefaults.outlinedTextFieldColors( textColor = Color.Black, placeholderColor = Color.Gray, cursorColor = DarkBlue, focusedBorderColor = DarkBlue, unfocusedBorderColor = Color.Gray),
                        value = email.value,
                        onValueChange = {
                            email.value = it
                        },
                        label = {
                            Text(text =stringResource(id = R.string.email))
                        },
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Done
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(modifier = Modifier
                            .border(1.dp, Yellow, shape = RoundedCornerShape(size = 40.dp))
                            .size(115.dp, 40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            onClick = {
                                isForgotPasswordDialogOpen.value = false
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        val context = LocalContext.current
                        Button(modifier = Modifier.size(160.dp,40.dp), colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                            onClick = {
                                if (email.value.isNotBlank()) {
                                    val emailAddress = email.value
                                    // Reset password logic
                                    FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                isForgotPasswordDialogOpen.value = false
                                                Toast.makeText(
                                                    context,
                                                    "Parola sıfırlama e-postası başarıyla gönderildi",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                isForgotPasswordDialogOpen.value = false
                                                Toast.makeText(
                                                    context,
                                                    "Parola sıfırlanamadı: ${task.exception?.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Lütfen e-mail adresinizi giriniz",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) {
                            Text(text = stringResource(id = R.string.reset),color= Color.Black, fontSize = 16.sp, textAlign = TextAlign.Center)
                        }
                        Spacer(modifier = Modifier.width(2.dp))
                        Button(modifier = Modifier
                            .border(1.dp, Yellow, shape = RoundedCornerShape(size = 40.dp))
                            .size(115.dp, 40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            onClick = {
                                isForgotPasswordDialogOpen.value = false
                            }
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun LoginScreenPreviewLight() {
    RetroAppTheme() {
        LoginScreen(null, rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreviewDark() {
    RetroAppTheme() {
        LoginScreen(null, rememberNavController())
    }
}