package com.zeroillusion.shopapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zeroillusion.shopapp.model.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

  //  @Query("SELECT * FROM user WHERE first_name IN (:firstName) AND email IN (:email)")
  //  suspend fun checkUser(firstName: String, email: String): User?

    @Query("SELECT * FROM user WHERE first_name IN (:firstName)")
    suspend fun checkUser(firstName: String): User?

    @Query("SELECT * FROM user WHERE email IN (:email)")
    suspend fun checkEmail(email: String): User?
}