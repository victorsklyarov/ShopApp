package com.zeroillusion.shopapp.core.data.local

import androidx.room.*
import com.zeroillusion.shopapp.core.data.local.entity.UserEntity

@Dao
interface CurrentUserDao {

    @Insert(entity = UserEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCurrentUser(user: UserEntity)

    @Query("SELECT * FROM userentity LIMIT 1")
    suspend fun getCurrentUser(): UserEntity?

    @Query("DELETE FROM userentity")
    suspend fun resetCurrentUser()
}