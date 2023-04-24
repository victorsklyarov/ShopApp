package com.zeroillusion.shopapp.core.domain.model

data class CurrentUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val image: ByteArray
)