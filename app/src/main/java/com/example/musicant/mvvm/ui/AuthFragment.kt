package com.example.musicant.mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicant.MainActivity
import com.example.musicant.R
import com.example.musicant.mvvm.MvvmViewModel
import com.google.android.material.textfield.TextInputLayout

class AuthFragment : Fragment() {

    private lateinit var viewModel: MvvmViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_auth_mvvm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[(MvvmViewModel::class.java)]

        val singUpButton: AppCompatButton = view.findViewById(R.id.button_sing_up)
        val nameField: TextInputLayout = view.findViewById(R.id.input_layout_name)
        val emailField: TextInputLayout = view.findViewById(R.id.input_layout_email)
        val passwordField: TextInputLayout = view.findViewById(R.id.input_layout_password)
        val confirmPasswordField: TextInputLayout =
            view.findViewById(R.id.input_layout_confirm_password)

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
        viewModel.isSingUpSuccessLiveData.observe(viewLifecycleOwner, {
            (activity as MainActivity).openNextFragment(SuccessFragment())
        })
        viewModel.isSingUpFailLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Something wrong. Please, retry!", Toast.LENGTH_LONG).show()
        })
    }
}