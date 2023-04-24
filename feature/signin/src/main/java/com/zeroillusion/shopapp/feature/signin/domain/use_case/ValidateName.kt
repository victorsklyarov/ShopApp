package com.zeroillusion.shopapp.feature.signin.domain.use_case

class ValidateName {

    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The name can't be blank"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}