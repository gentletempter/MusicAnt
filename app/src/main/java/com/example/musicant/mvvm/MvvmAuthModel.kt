package com.example.musicant.mvvm

class MvvmAuthModel {
    fun onSingUpClicked(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return checkName(name) && checkEmail(email) && checkPassword(password, confirmPassword)
    }

    private fun checkName(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun checkEmail(email: String): Boolean {
        return email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun checkPassword(password: String, confirmPassword: String): Boolean {
        val isValidPassword = password.isNotBlank() && password.length >= 5
        return isValidPassword && password == confirmPassword
    }
}