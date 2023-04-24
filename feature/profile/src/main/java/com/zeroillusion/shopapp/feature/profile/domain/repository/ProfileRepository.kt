package com.zeroillusion.shopapp.feature.profile.domain.repository

import com.zeroillusion.shopapp.core.domain.model.User

interface ProfileRepository {

    suspend fun updateUser(user: User)

    suspend fun resetCurrentUser()
}