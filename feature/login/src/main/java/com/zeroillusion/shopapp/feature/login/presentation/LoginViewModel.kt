package com.zeroillusion.shopapp.feature.login.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.core.domain.use_case.*
import com.zeroillusion.shopapp.core.presentation.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUser: GetUser
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(UserState())
    val state: State<UserState> = _state

    var loginState by mutableStateOf(LoginState())

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                loginState = loginState.copy(email = event.email)
            }

            is LoginEvent.PasswordChanged -> {
                loginState = loginState.copy(password = event.password)
            }

            is LoginEvent.Login -> {
                loginUser()
            }
        }
    }

    private var getUserJob: Job? = null

    fun loginUser() {
        getUserJob?.cancel()
        getUserJob = viewModelScope.launch {
            getUser(
                loginState.email,
                loginState.password.hashCode().toString()
            ).onEach { result ->
                delay(500L)
                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            user = result.data,
                            isLoading = false
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
                        loginState = loginState.copy(
                            password = ""
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
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object NavigateToHome : UIEvent()
    }
}