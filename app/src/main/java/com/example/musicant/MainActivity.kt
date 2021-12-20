package com.example.musicant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.musicant.mvvm.ui.auth.AuthFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openNextFragment(AuthFragment())
    }

    fun openNextFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment, fragment.toString())
            .addToBackStack(null)
            .commit()
    }
}