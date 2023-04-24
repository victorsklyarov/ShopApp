package com.zeroillusion.shopapp.feature.signin.domain.use_case

import android.util.Patterns
import com.zeroillusion.shopapp.feature.signin.domain.repository.SignInRepository

class ValidateEmail(
    private val repository: SignInRepository
) {

    suspend operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }
        val isEmailExists = repository.isEmailExists(email.lowercase())
        if (isEmailExists) {
            return ValidationResult(
                successful = false,
                errorMessage = "User with this email already exist"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}