package com.zeroillusion.shopapp.feature.profile.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeroillusion.shopapp.core.domain.model.CurrentUser
import com.zeroillusion.shopapp.core.domain.model.User
import com.zeroillusion.shopapp.core.domain.use_case.*
import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.core.presentation.ProfileState
import com.zeroillusion.shopapp.feature.profile.domain.use_case.ResetCurrentUser
import com.zeroillusion.shopapp.feature.profile.domain.use_case.UpdateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val resetCurrentUser: ResetCurrentUser,
    private val updateUser: UpdateUser
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _currentUserState = mutableStateOf(ProfileState())
    val currentUserState: State<ProfileState> = _currentUserState

    private val _currentUser = MutableStateFlow<CurrentUser?>(null)
    val currentUser: StateFlow<CurrentUser?> = _currentUser

    private var getCurrentUserJob: Job? = null

    init {
        getCurrentUserData()
    }

    fun updateCurrentUser(user: CurrentUser) {
        _currentUser.value = user
        updateUserData()
    }

    fun logOut() {
        viewModelScope.launch {
            resetCurrentUser()
            _eventFlow.emit(
                UIEvent.NavigateToSignIn
            )
        }
    }

    private fun getCurrentUserData() {
        getCurrentUserJob?.cancel()
        getCurrentUserJob = viewModelScope.launch {
            getCurrentUser().onEach { result ->
                delay(500L)
                when (result) {
                    is Resource.Success -> {
                        _currentUser.value = result.data
                        _currentUserState.value = currentUserState.value.copy(
                            currentUser = result.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _currentUserState.value = currentUserState.value.copy(
                            currentUser = result.data,
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Loading -> {
                        _currentUserState.value = currentUserState.value.copy(
                            currentUser = result.data,
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun updateUserData() {
        viewModelScope.launch {
            currentUser.value?.let { currentUser ->
                updateUser(
                    User(
                        firstName = currentUser.firstName,
                        lastName = currentUser.lastName,
                        email = currentUser.email,
                        password = currentUser.password,
                        image = currentUser.image
                    )
                )
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object NavigateToSignIn : UIEvent()
    }
}