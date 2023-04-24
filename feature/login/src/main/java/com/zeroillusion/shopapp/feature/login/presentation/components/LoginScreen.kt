package com.zeroillusion.shopapp.feature.login.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zeroillusion.shopapp.feature.login.presentation.LoginViewModel
import com.zeroillusion.shopapp.core.presentation.components.InputField
import com.zeroillusion.shopapp.feature.login.presentation.LoginEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.state.value
    val loginState = viewModel.loginState
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

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
                            is LoginViewModel.UIEvent.ShowSnackbar -> {
                                snackbarHostState.showSnackbar(
                                    message = event.message
                                )
                            }

                            is LoginViewModel.UIEvent.NavigateToHome -> {
                                navController.navigate("home_screen") {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(280.dp)
                ) {
                    Spacer(modifier = Modifier.height(100.dp))
                    Text(
                        text = "Welcome back",
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    InputField(
                        value = loginState.email,
                        onValueChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
                        placeholder = "Email",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )

                    InputField(
                        value = loginState.password,
                        onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
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
                    Spacer(modifier = Modifier.height(120.dp))
                    Button(
                        onClick = { viewModel.loginUser() },
                        modifier = Modifier
                            .height(45.dp)
                            .width(280.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}