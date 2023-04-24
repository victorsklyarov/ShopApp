package com.zeroillusion.shopapp.core.data.local

import androidx.room.*
import com.zeroillusion.shopapp.core.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(entity = UserEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM userentity WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM userentity WHERE email LIKE :email AND password LIKE:password LIMIT 1")
    suspend fun getUser(email: String, password: String): UserEntity?

    @Update
    suspend fun updateUser(user: UserEntity)
}