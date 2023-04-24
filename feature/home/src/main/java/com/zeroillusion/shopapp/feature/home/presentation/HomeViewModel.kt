package com.zeroillusion.shopapp.feature.home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeroillusion.shopapp.core.domain.model.CurrentUser
import com.zeroillusion.shopapp.core.domain.use_case.GetCurrentUser
import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.core.presentation.ProfileState
import com.zeroillusion.shopapp.feature.home.domain.model.Category
import com.zeroillusion.shopapp.feature.home.domain.use_case.GetProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProducts: GetProducts,
    private val getCurrentUser: GetCurrentUser
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _currentUserState = mutableStateOf(ProfileState())
    val currentUserState: State<ProfileState> = _currentUserState

    private val _currentUser = MutableStateFlow<CurrentUser?>(null)
    val currentUser: StateFlow<CurrentUser?> = _currentUser

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private var getProductsJob: Job? = null

    init {
        getProductsData()
        getCurrentUserData()
    }

    private fun getProductsData() {
        getProductsJob?.cancel()
        getProductsJob = viewModelScope.launch {
            getProducts().onEach { result ->
                delay(500L)
                when (result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            latestProductItems = result.data?.latestProductList ?: emptyList(),
                            flashSaleProductItems = result.data?.flashSaleProductList
                                ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            latestProductItems = result.data?.latestProductList ?: emptyList(),
                            flashSaleProductItems = result.data?.flashSaleProductList
                                ?: emptyList(),
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
                            latestProductItems = result.data?.latestProductList ?: emptyList(),
                            flashSaleProductItems = result.data?.flashSaleProductList
                                ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private var getCurrentUserJob: Job? = null

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

    fun setCategories(items: List<Category>) {
        _categories.value = items
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
    }
}