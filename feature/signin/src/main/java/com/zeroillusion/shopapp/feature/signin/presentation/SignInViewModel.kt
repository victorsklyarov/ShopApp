package com.zeroillusion.shopapp.feature.signin.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeroillusion.shopapp.core.domain.model.CurrentUser
import com.zeroillusion.shopapp.core.domain.model.User
import com.zeroillusion.shopapp.core.domain.use_case.*
import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.core.presentation.UserState
import com.zeroillusion.shopapp.feature.signin.domain.use_case.AddUser
import com.zeroillusion.shopapp.feature.signin.domain.use_case.ValidateEmail
import com.zeroillusion.shopapp.feature.signin.domain.use_case.ValidateName
import com.zeroillusion.shopapp.feature.signin.domain.use_case.ValidatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val addUser: AddUser,
    private val getUser: GetUser,
    private val getCurrentUser: GetCurrentUser,
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(UserState())
    val state: State<UserState> = _state

    private var currentUser by mutableStateOf<CurrentUser?>(null)

    var defaultImage by mutableStateOf(byteArrayOf())

    var signInState by mutableStateOf(SignInState())

    private var registerUserJob: Job? = null
    private var autoLoginJob: Job? = null

    init {
        autoLogin()
    }

    fun registerUser() {
        registerUserJob?.cancel()
        registerUserJob = viewModelScope.launch {
            val firstNameResult = validateName(signInState.firstName)
            val lastNameResult = validateName(signInState.lastName)
            val emailResult = validateEmail(signInState.email)
            val passwordResult = validatePassword(signInState.password)

            val isError = listOf(
                firstNameResult,
                lastNameResult,
                emailResult,
                passwordResult
            ).any { !it.successful }

            if (isError) {
                signInState = signInState.copy(
                    firstNameError = firstNameResult.errorMessage,
                    lastNameError = lastNameResult.errorMessage,
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage
                )
                return@launch
            }
            addUser(
                User(
                    firstName = signInState.firstName.trimStart().trimEnd()
                        .replace("\\s+".toRegex(), " "),
                    lastName = signInState.lastName.trimStart().trimEnd()
                        .replace("\\s+".toRegex(), " "),
                    email = signInState.email.lowercase(),
                    password = signInState.password.hashCode().toString(),
                    image = defaultImage
                )
            )
            _eventFlow.emit(
                UIEvent.NavigateToHome
            )
        }
    }

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.FirstNameChanged -> {
                signInState = signInState.copy(firstName = event.firstName)
            }

            is SignInEvent.LastNameChanged -> {
                signInState = signInState.copy(lastName = event.lastName)
            }

            is SignInEvent.EmailChanged -> {
                signInState = signInState.copy(email = event.email)
            }

            is SignInEvent.PasswordChanged -> {
                signInState = signInState.copy(password = event.password)
            }

            is SignInEvent.SignIn -> {
                registerUser()
            }
        }
    }

    private fun autoLogin() {
        _state.value = state.value.copy(
            user = null,
            isLoading = true
        )
        autoLoginJob?.cancel()
        autoLoginJob = viewModelScope.launch {
            getCurrentUser().onEach { getCurrentUserResult ->
                delay(500L)
                when (getCurrentUserResult) {
                    is Resource.Success -> {
                        currentUser = getCurrentUserResult.data
                        getUser(
                            currentUser?.email!!,
                            currentUser?.password!!
                        ).onEach { result ->
                            delay(500L)
                            when (result) {
                                is Resource.Success -> {
                                    _state.value = state.value.copy(
                                        user = result.data,
                                        isLoading = true
                                    )
                                    _eventFlow.emit(
                                        UIEvent.NavigateToHome
                                    )
                                }

                                is Resource.Error -> {
                                    _state.value = state.value.copy(
                                        user = result.data,
                                        isLoading = false
                                    )
                                    _eventFlow.emit(
                                        UIEvent.ShowSnackbar(
                                            result.message ?: "Unknown error"
                                        )
                                    )
                                }

                                is Resource.Loading -> {
                                    _state.value = state.value.copy(
                                        user = result.data,
                                        isLoading = true
                                    )
                                }
                            }
                        }.launchIn(viewModelScope)
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            user = null,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            user = null,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object NavigateToHome : UIEvent()
    }
}