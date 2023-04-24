package com.zeroillusion.shopapp.feature.signin.domain.repository

import com.zeroillusion.shopapp.core.domain.model.User

interface SignInRepository {

    suspend fun insertUser(user: User)

    suspend fun isEmailExists(email: String): Boolean
}