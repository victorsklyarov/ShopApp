package com.zeroillusion.shopapp.feature.signin.data.repository

import com.zeroillusion.shopapp.core.data.local.CurrentUserDao
import com.zeroillusion.shopapp.core.data.local.UserDao
import com.zeroillusion.shopapp.core.data.local.entity.UserEntity
import com.zeroillusion.shopapp.core.domain.model.User
import com.zeroillusion.shopapp.feature.signin.domain.repository.SignInRepository

class SignInRepositoryImpl(
    private val dao: UserDao,
    private val currentUserDao: CurrentUserDao
) : SignInRepository {

    override suspend fun insertUser(user: User) {
        dao.insertUser(
            UserEntity(
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                password = user.password,
                image = user.image
            )
        )
        val currentUser = dao.getUserByEmail(user.email)
        return currentUserDao.setCurrentUser(currentUser!!)
    }

    override suspend fun isEmailExists(email: String): Boolean {
        return dao.getUserByEmail(email) != null
    }
}