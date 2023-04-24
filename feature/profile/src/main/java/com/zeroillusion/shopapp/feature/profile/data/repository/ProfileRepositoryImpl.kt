package com.zeroillusion.shopapp.feature.profile.data.repository

import com.zeroillusion.shopapp.core.data.local.CurrentUserDao
import com.zeroillusion.shopapp.core.data.local.UserDao
import com.zeroillusion.shopapp.core.data.local.entity.UserEntity
import com.zeroillusion.shopapp.core.domain.model.User
import com.zeroillusion.shopapp.feature.profile.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val dao: UserDao,
    private val currentUserDao: CurrentUserDao
) : ProfileRepository {

    override suspend fun updateUser(user: User) {
        val currentUser = currentUserDao.getCurrentUser()!!
        val updatedUser = UserEntity(
            id = currentUser.id,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            password = user.password,
            image = user.image
        )
        currentUserDao.setCurrentUser(updatedUser)
        return dao.updateUser(updatedUser)
    }

    override suspend fun resetCurrentUser() {
        return currentUserDao.resetCurrentUser()
    }
}