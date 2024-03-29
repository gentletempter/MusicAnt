package com.example.musicant.mvvm.model.network.auth

interface NetworkAuthService {
    fun onLoginClicked(email: String, password: String): String?
    fun updateUserData(data: Any)
}