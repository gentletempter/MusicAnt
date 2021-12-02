package com.example.musicant.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MvvmViewModel : ViewModel() {
    val isSingUpSuccessLiveData = MutableLiveData<Unit>()
    val isSingUpFailLiveData = MutableLiveData<Unit>()
    private val authModel = MvvmAuthModel()
    fun onSingUpClicked(name: String, email: String, password: String, confirmPassword: String) {
        val isSuccess = authModel.onSingUpClicked(name, email, password, confirmPassword)
        if (isSuccess) {
            isSingUpSuccessLiveData.postValue(Unit)
        } else {
            isSingUpFailLiveData.postValue(Unit)
        }
    }
}