package com.zeroillusion.shopapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeroillusion.shopapp.dao.UserDao
import com.zeroillusion.shopapp.model.User

@Database(entities = [User::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
}