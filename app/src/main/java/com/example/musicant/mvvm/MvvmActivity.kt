package com.example.musicant.mvvm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.musicant.R
import androidx.lifecycle.ViewModelProvider
import com.example.musicant.success.SuccessActivity
import com.google.android.material.textfield.TextInputLayout

class MvvmActivity : AppCompatActivity() {
    private lateinit var viewModel: MvvmViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)

        viewModel = ViewModelProvider(this)[(MvvmViewModel::class.java)]

        val singUpButton: AppCompatButton = findViewById(R.id.button_sing_up)
        val nameField: TextInputLayout = findViewById(R.id.input_layout_name)
        val emailField: TextInputLayout = findViewById(R.id.input_layout_email)
        val passwordField: TextInputLayout = findViewById(R.id.input_layout_password)
        val confirmPasswordField: TextInputLayout = findViewById(R.id.input_layout_confirm_password)

        singUpButton.setOnClickListener {
            val nameText = nameField.editText?.text.toString()
            val emailText = emailField.editText?.text.toString()
            val passwordText = passwordField.editText?.text.toString()
            val confirmPasswordText = confirmPasswordField.editText?.text.toString()
            viewModel.onSingUpClicked(nameText, emailText, passwordText, confirmPasswordText)
        }
        subscribeOnLiveData()
    }

    private fun subscribeOnLiveData() {
        viewModel.isSingUpSuccessLiveData.observe(this, {
            startActivity(Intent(this, SuccessActivity::class.java))
        })
        viewModel.isSingUpFailLiveData.observe(this, {
            Toast.makeText(this, "Something wrong", Toast.LENGTH_LONG).show()
        })
    }

}