package com.example.retroapp.presentation.auth

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.retroapp.presentation.ui.theme.LightGray
import com.example.retroapp.presentation.ui.theme.RetroAppTheme
import com.example.retroapp.presentation.ui.theme.Yellow
import com.example.retroapp.presentation.ui.theme.spacing
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: AuthViewModel?, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            value = email,
            onValueChange = {
                email = it
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
                }.background(LightGray),
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
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.constrainAs(refPassword) {
                top.linkTo(refEmail.bottom, spacing.medium)
                start.linkTo(parent.start, spacing.large)
                end.linkTo(parent.end, spacing.large)
                width = Dimension.fillToConstraints
            }.background(LightGray),
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
            )
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
                viewModel?.login(email, password)
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
                color= Color.White
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
            if (!emailDialogOpen.value) {
                AlertDialog(
                    onDismissRequest = {
                        isForgotPasswordDialogOpen.value = false
                    },
                    title = {
                        Text(text = "Forgot Password")
                    },
                    text = {
                        Text(text = "Please enter your email address to reset your password.")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                emailDialogOpen.value = true
                            }
                        ) {
                            Text(text = "Reset Password")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                isForgotPasswordDialogOpen.value = false
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                )
            } else {
                AlertDialog(
                    onDismissRequest = {
                        isForgotPasswordDialogOpen.value = false
                    },
                    title = {
                        Text(text = "Forgot Password")
                    },
                    text = {
                        TextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            label = {
                                Text(text = "E-mail")
                            },
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.None,
                                autoCorrect = false,
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Done
                            )
                        )
                    },
                    confirmButton = {
                        val context = LocalContext.current
                        Button(
                            onClick = {
                                val emailAddress = email
                                // Reset password logic
                                FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                                    .addOnCompleteListener { task ->

                                        if (task.isSuccessful) {
                                            isForgotPasswordDialogOpen.value = false
                                            Toast.makeText(
                                                context,
                                                "Password reset email sent successfully.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            isForgotPasswordDialogOpen.value = false
                                            Toast.makeText(
                                                context,
                                                "Password reset failed: ${task.exception?.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            }
                        ) {
                            Text(text = "Reset Password")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                isForgotPasswordDialogOpen.value = false
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                )
            }
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