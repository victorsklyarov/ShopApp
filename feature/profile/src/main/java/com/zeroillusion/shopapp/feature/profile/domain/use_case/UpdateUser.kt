package com.zeroillusion.shopapp.feature.profile.domain.use_case

import com.zeroillusion.shopapp.core.domain.model.User
import com.zeroillusion.shopapp.feature.profile.domain.repository.ProfileRepository

class UpdateUser(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(user: User) {
        return repository.updateUser(user)
    }
}