package com.example.musicant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.musicant.mvvm.ui.auth.AuthFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val NAVIGATION_EVENT = "navigation_event"
        const val NAVIGATION_EVENT_DATA_KEY = "navigation_event_data_key"
    }

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