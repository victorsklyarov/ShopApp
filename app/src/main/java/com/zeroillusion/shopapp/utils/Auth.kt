package com.zeroillusion.shopapp.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import androidx.room.Room
import com.zeroillusion.shopapp.data.Database
import com.zeroillusion.shopapp.model.User

class Auth(context: Context) {

    private val db = Room.databaseBuilder(context, Database::class.java, "database").build()
    private val userDao = db.userDao()

    suspend fun login(firstName: String, password: String): Boolean {
        val result = userDao.checkUser(firstName) != null
        db.close()
        return result
    }

    suspend fun signIn(firstName: String, lastName: String, email: String): Int {
        val result =
            if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (userDao.checkEmail(email) != null) {
                    -1
                } else {
                    userDao.insertUser(User(0, firstName, lastName, email))
                    1
                }
            } else {
                0
            }
        db.close()
        return result
    }
}