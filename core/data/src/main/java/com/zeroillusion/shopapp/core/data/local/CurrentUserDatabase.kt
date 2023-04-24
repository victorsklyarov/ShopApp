package com.zeroillusion.shopapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeroillusion.shopapp.core.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class CurrentUserDatabase : RoomDatabase() {

    abstract val dao: CurrentUserDao
}