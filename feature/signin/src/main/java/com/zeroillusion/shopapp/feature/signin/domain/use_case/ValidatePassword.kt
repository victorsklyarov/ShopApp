package com.zeroillusion.shopapp.feature.signin.domain.use_case

class ValidatePassword {

    operator fun invoke(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password must contain at least 8 characters"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}