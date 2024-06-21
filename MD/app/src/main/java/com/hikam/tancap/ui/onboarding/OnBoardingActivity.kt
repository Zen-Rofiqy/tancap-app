package com.hikam.tancap.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hikam.tancap.R

class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        // Add the ViewPageFragment to the container
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ViewPageFragment())
                .commit()
        }
    }
}
