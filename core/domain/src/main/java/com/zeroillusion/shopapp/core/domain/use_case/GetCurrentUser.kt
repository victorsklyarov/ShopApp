package com.zeroillusion.shopapp.core.domain.use_case

import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.core.domain.model.CurrentUser
import com.zeroillusion.shopapp.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentUser(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Flow<Resource<CurrentUser>> {
        return repository.getCurrentUser()
    }
}