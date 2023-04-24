package com.zeroillusion.shopapp.core.domain.repository

import com.zeroillusion.shopapp.core.domain.model.CurrentUser
import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUser(email: String, password: String): Flow<Resource<User>>

    suspend fun getCurrentUser(): Flow<Resource<CurrentUser>>
}