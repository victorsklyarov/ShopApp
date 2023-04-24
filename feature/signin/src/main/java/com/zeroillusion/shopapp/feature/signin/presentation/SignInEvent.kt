package com.zeroillusion.shopapp.feature.signin.presentation

sealed class SignInEvent {
    data class FirstNameChanged(val firstName: String) : SignInEvent()
    data class LastNameChanged(val lastName: String) : SignInEvent()
    data class EmailChanged(val email: String) : SignInEvent()
    data class PasswordChanged(val password: String) : SignInEvent()
    object SignIn : SignInEvent()
}