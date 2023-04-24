package com.zeroillusion.shopapp.core.presentation

import com.zeroillusion.shopapp.core.domain.model.CurrentUser

data class ProfileState(
    val currentUser: CurrentUser? = null,
    val isLoading: Boolean = false
)