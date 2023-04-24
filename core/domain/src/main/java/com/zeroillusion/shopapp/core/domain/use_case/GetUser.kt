package com.zeroillusion.shopapp.core.domain.use_case

import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.core.domain.model.User
import com.zeroillusion.shopapp.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUser(
    private val repository: UserRepository
) {
    operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<User>> {
        return repository.getUser(email, password)
    }
}