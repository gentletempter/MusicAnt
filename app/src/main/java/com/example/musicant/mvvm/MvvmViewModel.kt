package com.example.musicant.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MvvmViewModel : ViewModel() {
    val isSignUpSuccessLiveData = MutableLiveData<Unit>()
    val isSignUpFailLiveData = MutableLiveData<Unit>()
    private val authModel = MvvmAuthModel()
    fun onSingUpClicked(name: String, email: String, password: String, confirmPassword: String) {
        val isSuccess = authModel.onSignUpClicked(name, email, password, confirmPassword)
        if (isSuccess) {
            isSignUpSuccessLiveData.postValue(Unit)
        } else {
            isSignUpFailLiveData.postValue(Unit)
        }
    }
}