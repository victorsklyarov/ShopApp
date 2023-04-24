package com.zeroillusion.shopapp.core.data.repository

import com.zeroillusion.shopapp.core.domain.utils.Resource
import com.zeroillusion.shopapp.core.data.local.CurrentUserDao
import com.zeroillusion.shopapp.core.data.local.UserDao
import com.zeroillusion.shopapp.core.domain.model.CurrentUser
import com.zeroillusion.shopapp.core.domain.model.User
import com.zeroillusion.shopapp.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val dao: UserDao,
    private val currentUserDao: CurrentUserDao
) : UserRepository {

    override fun getUser(email: String, password: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        val userEntity = dao.getUser(email, password)
        emit(Resource.Loading(data = userEntity?.toUser()))
        if (userEntity != null) {
            currentUserDao.setCurrentUser(userEntity)
            emit(
                Resource.Success(
                    User(
                        firstName = userEntity.firstName,
                        lastName = userEntity.lastName,
                        email = userEntity.email,
                        password = userEntity.password,
                        image = userEntity.image
                    )
                )
            )
        } else {
            emit(
                Resource.Error(
                    message = "The user does not exist or the password is incorrect",
                    data = null
                )
            )
        }
    }

    override suspend fun getCurrentUser(): Flow<Resource<CurrentUser>> = flow {
        emit(Resource.Loading())
        val currentUser = currentUserDao.getCurrentUser()?.toCurrentUser()
        emit(Resource.Loading(data = currentUser))
        if (currentUser != null) {
            emit(
                Resource.Success(
                    CurrentUser(
                        id = currentUser.id,
                        firstName = currentUser.firstName,
                        lastName = currentUser.lastName,
                        email = currentUser.email,
                        password = currentUser.password,
                        image = currentUser.image
                    )
                )
            )
        } else {
            emit(
                Resource.Error(
                    message = "Сurrent user is missing",
                    data = null
                )
            )
        }
    }
}