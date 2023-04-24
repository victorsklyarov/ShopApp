package com.zeroillusion.shopapp.feature.signin.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)