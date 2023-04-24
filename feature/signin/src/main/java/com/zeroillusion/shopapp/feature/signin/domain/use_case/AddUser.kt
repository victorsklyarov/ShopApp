package com.zeroillusion.shopapp.feature.signin.domain.use_case

import com.zeroillusion.shopapp.core.domain.model.User
import com.zeroillusion.shopapp.feature.signin.domain.repository.SignInRepository

class AddUser(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(user: User) {
        return repository.insertUser(user)
    }
}