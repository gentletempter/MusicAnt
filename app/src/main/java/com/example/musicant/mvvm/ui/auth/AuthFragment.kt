package com.example.musicant.mvvm.ui.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.musicant.MainActivity
import com.example.musicant.R
import com.example.musicant.mvvm.model.storage.preferences.AppPreferencesImpl
import com.example.musicant.mvvm.ui.music.ArtistsFragment
import com.google.android.material.textfield.TextInputLayout
import java.io.File

class AuthFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var progress: ProgressBar
    private lateinit var overlay: FrameLayout
    private lateinit var loginField: TextInputLayout
    private lateinit var passwordField: TextInputLayout
    private lateinit var buttonLogin: AppCompatButton
    private lateinit var saveCredentialsCheckBox: AppCompatCheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        viewModel.setSharedPreferences(AppPreferencesImpl.getInstance(requireContext()))
        viewModel.fetchStoredData()

        requireActivity().supportFragmentManager.setFragmentResult(
            MainActivity.NAVIGATION_EVENT,
            bundleOf(MainActivity.NAVIGATION_EVENT_DATA_KEY to "AuthFragment Created")
        )

        buttonLogin = view.findViewById(R.id.button_login)
        loginField = view.findViewById(R.id.input_layout_login)
        passwordField = view.findViewById(R.id.input_layout_password)
        progress = view.findViewById(R.id.progress)
        overlay = view.findViewById(R.id.overlay_container)
        saveCredentialsCheckBox = view.findViewById(R.id.save_credentials_check_box)

        if (loginField.editText?.text?.isBlank() == true) {
            ContextCompat.getColorStateList(
                requireContext(),
                R.color.auth_input_layout_stroke_color_default
            )?.let {
                loginField.setBoxStrokeColorStateList(it)
            }
        } else {
            ContextCompat.getColorStateList(
                requireContext(),
                R.color.auth_input_layout_stroke_color
            )?.let { colorList ->
                loginField.setBoxStrokeColorStateList(colorList)
            }
        }

        initListeners()
        subscribeOnLiveData()
    }

    private fun initListeners() {
        loginField.editText?.addTextChangedListener {
            it?.let {
                viewModel.setUpdatedEmail(it.toString())
                if (it.isBlank()) {
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.auth_input_layout_stroke_color_default
                    )?.let { colorList ->
                        loginField.setBoxStrokeColorStateList(colorList)
                    }
                } else {
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.auth_input_layout_stroke_color
                    )?.let { colorList ->
                        loginField.setBoxStrokeColorStateList(colorList)
                    }
                }
            }
        }
        passwordField.editText?.addTextChangedListener {
            viewModel.setUpdatedPassword(it.toString())
        }
        buttonLogin.setOnClickListener {
            val emailText = loginField.editText?.text.toString()
            val passwordText = passwordField.editText?.text.toString()
            viewModel.onLoginClicked(emailText, passwordText)
            viewModel.saveCredentialsToFile(
                emailText,
                passwordText,
                File(
                    requireActivity().getDir(
                        "credentials",
                        Context.MODE_PRIVATE
                    ).absolutePath + "/" + "credentials.txt"
                )
            )
        }
        saveCredentialsCheckBox.setOnCheckedChangeListener { _, selected ->
            viewModel.setSaveCredentialsSelected(selected)
        }
    }

    private fun subscribeOnLiveData() {
        viewModel.isLoginSuccessLiveData.observe(viewLifecycleOwner, {
            (activity as MainActivity).openNextFragment(ArtistsFragment())
        })
        viewModel.isLoginFailedLiveData.observe(viewLifecycleOwner, {
            Toast.makeText(context, "Something went wrong. Please, retry!", Toast.LENGTH_LONG)
                .show()
        })
        viewModel.showProgressLiveData.observe(viewLifecycleOwner, {
            showProgress()
        })
        viewModel.hideProgressLiveData.observe(viewLifecycleOwner, {
            hideProgress()
        })
        viewModel.saveCredentialsCheckedLiveData.observe(viewLifecycleOwner, { isSelected ->
            saveCredentialsCheckBox.isChecked = isSelected
        })
        viewModel.emailLiveData.observe(viewLifecycleOwner, { email ->
            loginField.editText?.setText(email)
            loginField.editText?.setSelection(email.length)
        })
        viewModel.passwordLiveData.observe(viewLifecycleOwner, { password ->
            passwordField.editText?.setText(password)
            passwordField.editText?.setSelection(password.length)
        })
    }

    private fun hideProgress() {
        progress.isVisible = false
        overlay.isVisible = false
    }

    private fun showProgress() {
        progress.isVisible = true
        overlay.isVisible = true
    }
}