package com.zeroillusion.shopapp.feature.profile.domain.use_case

import com.zeroillusion.shopapp.feature.profile.domain.repository.ProfileRepository

class ResetCurrentUser(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() {
        return repository.resetCurrentUser()
    }
}