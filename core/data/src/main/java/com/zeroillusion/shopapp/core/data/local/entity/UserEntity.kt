package com.zeroillusion.shopapp.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zeroillusion.shopapp.core.domain.model.CurrentUser
import com.zeroillusion.shopapp.core.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val image: ByteArray
) {
    fun toUser(): User {
        return User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            image = image
        )
    }

    fun toCurrentUser(): CurrentUser {
        return CurrentUser(
            id = id!!,
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            image = image
        )
    }
}
