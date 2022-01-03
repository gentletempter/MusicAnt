package com.example.musicant.mvvm.ui.auth

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicant.mvvm.model.network.auth.NetworkAuthService
import com.example.musicant.mvvm.model.network.auth.NetworkAuthServiceImpl
import com.example.musicant.mvvm.model.storage.preferences.AppPreferences
import java.io.*

class AuthViewModel : ViewModel(), LifecycleObserver {

    companion object {
        private const val TAG = "AuthViewModel"
    }

    val isLoginSuccessLiveData = MutableLiveData<Unit>()
    val isLoginFailedLiveData = MutableLiveData<Unit>()
    val showProgressLiveData = MutableLiveData<Unit>()
    val hideProgressLiveData = MutableLiveData<Unit>()

    val emailLiveData = MutableLiveData<String>()
    val passwordLiveData = MutableLiveData<String>()
    val saveCredentialsCheckedLiveData = MutableLiveData<Boolean>()

    private val authModel: NetworkAuthService = NetworkAuthServiceImpl()
    //private val storageModel: UserStorage = LocalStorageModel()
    private var preferences: AppPreferences? = null

    fun setSharedPreferences(preferences: AppPreferences) {
        this.preferences = preferences
    }

    fun onLoginClicked(email: String, password: String) {
        showProgressLiveData.postValue(Unit)
        Handler(Looper.getMainLooper()).postDelayed({
            val token = authModel.onLoginClicked(email, password)

            hideProgressLiveData.postValue(Unit)
            if (token != null) {
                saveToken(token)
                saveCredentials(email, password)
                isLoginSuccessLiveData.postValue(Unit)
            } else {
                isLoginFailedLiveData.postValue(Unit)
            }
        }, 2000)
    }

    fun setSaveCredentialsSelected(isSelected: Boolean) {
        preferences?.setSaveCredentialsSelected(isSelected)
    }

    fun saveCredentialsToFile(email: String, password: String, file: File) {
        var fos: FileOutputStream? = null

        try {
            file.createNewFile()

            fos = FileOutputStream(file)
            fos.write("$email, $password".toByteArray())
        } catch (error: Exception) {
            Log.e(TAG, error.message ?: "")
        } finally {
            fos?.close()
            getCredentialsFromFile(file)
        }
    }

    private fun getCredentialsFromFile(file: File) {
        var fis: FileInputStream? = null

        try {
            fis = FileInputStream(file)
            val isr = InputStreamReader(fis)
            val bsr = BufferedReader(isr)
            val stringBuilder = StringBuilder()
            val iterator = bsr.lineSequence().iterator()

            while (iterator.hasNext()) {
                stringBuilder.append(iterator.next())
            }

            val result = stringBuilder.toString()

            if (result.isNotBlank()) {
                val credentials = result.split(",")

                val email = credentials[0]
                val password = credentials[1]

                println("CREDENTIALS: email = $email, password = $password")
            }
        } catch (error: Exception) {
            Log.e(TAG, error.message ?: "")
        } finally {
            fis?.close()
        }
    }

    private fun saveCredentials(email: String, password: String) {
        preferences?.let {
            if (it.isSaveCredentialsSelected()) {
                it.saveLogin(email)
                it.savePassword(password)
            }
        }
    }

    private fun saveToken(token: String) {
        preferences?.saveToken(token)
    }

    fun fetchStoredData() {
        preferences?.let {
            if (it.isSaveCredentialsSelected()) {
                emailLiveData.postValue(it.getLogin())
                passwordLiveData.postValue(it.getPassword())
                saveCredentialsCheckedLiveData.postValue(true)
            }
        }
    }

    fun setUpdatedEmail(email: String) {
        if (email != emailLiveData.value) {
            emailLiveData.value = email
        }
    }

    fun setUpdatedPassword(password: String) {
        if (password != passwordLiveData.value) {
            passwordLiveData.value = password
        }
    }
}