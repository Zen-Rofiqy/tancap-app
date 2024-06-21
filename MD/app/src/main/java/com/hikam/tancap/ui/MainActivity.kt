package com.hikam.tancap.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hikam.tancap.R
import com.hikam.tancap.databinding.ActivityMainBinding
import com.hikam.tancap.ui.onboarding.OnBoardingActivity
import com.hikam.tancap.viewmodel.MainViewModel
import com.hikam.tancap.viewmodel.NewsViewModel
import com.hikam.tancap.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(1000)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.homeFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_news -> {
                    navController.navigate(R.id.newsFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_catalog -> {
                    navController.navigate(R.id.catalogFragment)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                lifecycleScope.launch {
                    viewModel.logout()
                    val intent = Intent(this@MainActivity, OnBoardingActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.mode -> {
                val currentNightMode =
                    resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
                if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
