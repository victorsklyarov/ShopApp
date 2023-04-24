package com.zeroillusion.shopapp.core.presentation

import com.zeroillusion.shopapp.core.domain.model.User

data class UserState(
    val user: User? = null,
    val isLoading: Boolean = false
)