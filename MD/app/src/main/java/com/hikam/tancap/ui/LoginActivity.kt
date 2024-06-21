package com.hikam.tancap.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hikam.tancap.data.ResultState
import com.hikam.tancap.databinding.ActivityLoginBinding
import com.hikam.tancap.userpref.UserModel
import com.hikam.tancap.viewmodel.LoginViewModel
import com.hikam.tancap.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var customEditText: CustomEditText
    private lateinit var emailInputView: EmailInputView

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        playAnimation()
        setupAction()

        emailInputView = binding.emailEditText
        customEditText = binding.passwordEditText
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun playAnimation() {
        val rotateAnimator = ObjectAnimator.ofFloat(binding.imageView, View.ROTATION, 0f, 360f).apply {
            duration = 2000
        }

        val translateAnimator = ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }

        val fadeAnimators = mutableListOf<ObjectAnimator>()
        val viewsToAnimate = listOf(
            binding.titleTextView,
            binding.emailTextView,
            binding.emailEditTextLayout,
            binding.passwordTextView,
            binding.passwordEditTextLayout,
            binding.loginButton
        )
        viewsToAnimate.forEachIndexed { index, view ->
            fadeAnimators.add(ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).apply {
                duration = 500 // Durasi animasi
                startDelay = (index * 100).toLong() // Delay untuk setiap elemen
            })
        }

        val rotateLoginAnimator = ObjectAnimator.ofFloat(binding.loginButton, View.ROTATION, 0f, 360f).apply {
            duration = 1000
        }

        AnimatorSet().apply {
            playSequentially(rotateAnimator)
            playTogether(fadeAnimators as Collection<Animator>?)
            play(translateAnimator)
            play(rotateLoginAnimator)
            startDelay = 100
            start()
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("LoginActivity", "Attempting login with email: $email and password: $password")

            viewModel.login(email, password).observe(this) { user ->
                when (user) {
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeay!")
                            setMessage("Anda berhasil login")
                            setPositiveButton("Lanjut") { _, _ ->
                                saveSession(
                                    UserModel(
                                        user.data.token,
                                        email,
                                        password,
                                        true

                                    )
                                )
                            }
                            create()
                            show()
                        }
                    }
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        val error = user.error ?: "An unknown error occurred"
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        Log.e("LoginActivity", "Login failed with error: $error")
                    }
                }
            }
        }
    }

    private fun saveSession(session: UserModel) {
        lifecycleScope.launch {
            viewModel.saveSession(session)
            val sharedPref = getSharedPreferences("userSession", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("isLoggedIn", true)
            editor.apply()

            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ViewModelFactory.clearInstance()
            startActivity(intent)
        }
    }
}
