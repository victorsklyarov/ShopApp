package com.zeroillusion.shopapp.feature.signin.presentation.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zeroillusion.shopapp.core.presentation.components.InputField
import com.zeroillusion.shopapp.feature.signin.R
import com.zeroillusion.shopapp.feature.signin.presentation.SignInEvent
import com.zeroillusion.shopapp.feature.signin.presentation.SignInViewModel
import kotlinx.coroutines.flow.collectLatest
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.state.value
    val signInState = viewModel.signInState
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val defaultImageBitmap = BitmapFactory.decodeResource(
        LocalContext.current.resources,
        com.zeroillusion.shopapp.core.presentation.R.drawable.default_image
    )
    val stream = ByteArrayOutputStream()
    Bitmap
        .createScaledBitmap(defaultImageBitmap, 50, 50, true)
        .compress(Bitmap.CompressFormat.JPEG, 100, stream)
    viewModel.defaultImage = stream.toByteArray()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is SignInViewModel.UIEvent.ShowSnackbar -> {
                                snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }

                            is SignInViewModel.UIEvent.NavigateToHome -> {
                                navController.navigate("home_screen") {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
                if (state.isLoading) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(100.dp))
                        Text(
                            text = "Welcome",
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(280.dp)
                    ) {
                        Spacer(modifier = Modifier.height(100.dp))
                        Text(
                            text = "Sign In",
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                        InputField(
                            value = signInState.firstName,
                            onValueChange = { viewModel.onEvent(SignInEvent.FirstNameChanged(it)) },
                            isError = signInState.firstNameError != null,
                            supportingText = signInState.firstNameError,
                            placeholder = "First Name",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        InputField(
                            value = signInState.lastName,
                            onValueChange = { viewModel.onEvent(SignInEvent.LastNameChanged(it)) },
                            isError = signInState.lastNameError != null,
                            supportingText = signInState.lastNameError,
                            placeholder = "Last Name",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        InputField(
                            value = signInState.email,
                            onValueChange = { viewModel.onEvent(SignInEvent.EmailChanged(it)) },
                            isError = signInState.emailError != null,
                            supportingText = signInState.emailError,
                            placeholder = "Email",
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Email
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        InputField(
                            value = signInState.password,
                            onValueChange = { viewModel.onEvent(SignInEvent.PasswordChanged(it)) },
                            isError = signInState.passwordError != null,
                            supportingText = signInState.passwordError,
                            placeholder = "Password",
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = if (passwordVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            trailingIcon = @Composable {
                                val image =
                                    if (passwordVisible) com.zeroillusion.shopapp.core.presentation.R.drawable.ic_eye_on
                                    else com.zeroillusion.shopapp.core.presentation.R.drawable.ic_eye_off
                                val description =
                                    if (passwordVisible) "Hide password" else "Show password"
                                IconButton(
                                    onClick = { passwordVisible = !passwordVisible },
                                    modifier = Modifier
                                        .padding(end = 15.dp)
                                        .size(15.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = image), description,
                                    )
                                }
                            },
                            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                                top = 0.dp, bottom = 0.dp, start = 45.dp, end = 45.dp
                            )
                        )
                        Button(
                            onClick = { viewModel.registerUser() },
                            modifier = Modifier
                                .height(45.dp)
                                .width(280.dp)
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Text(
                                text = "Sign In",
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row {
                            Text(
                                text = "Already have an account?",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Login",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.Blue
                                ),
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate("login_screen")
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.height(70.dp))
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Row {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_google),
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Sign In with Google",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_apple),
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Sign In with Apple",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}