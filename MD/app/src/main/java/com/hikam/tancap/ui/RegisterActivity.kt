package com.hikam.tancap.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.hikam.tancap.viewmodel.ViewModelFactory
import com.hikam.tancap.data.ResultState
import com.hikam.tancap.databinding.ActivityRegisterBinding
import com.hikam.tancap.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var customEditText: CustomEditText

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupView()
        playAnimation()


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
        val rotateAnimator =
            ObjectAnimator.ofFloat(binding.imageView, View.ROTATION, 0f, 360f).apply {
                duration = 2000
            }

        val translateAnimator =
            ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
                duration = 1000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }

        val fadeAnimators = mutableListOf<ObjectAnimator>()
        val viewsToAnimate = listOf(
            binding.titleTextView,
            binding.nameTextView,
            binding.nameEditTextLayout,
            binding.emailTextView,
            binding.emailEditTextLayout,
            binding.passwordTextView,
            binding.passwordEditTextLayout,
            binding.signupButton
        )
        viewsToAnimate.forEachIndexed { index, view ->
            fadeAnimators.add(ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f).apply {
                duration = 500
                startDelay = (index * 100).toLong()
            })
        }

        val rotateSignupAnimator =
            ObjectAnimator.ofFloat(binding.signupButton, View.ROTATION, 0f, 360f).apply {
                duration = 1000
            }

        AnimatorSet().apply {
            playSequentially(rotateAnimator)
            playTogether(fadeAnimators as Collection<Animator>?)
            play(translateAnimator)
            play(rotateSignupAnimator)
            startDelay = 100
            start()
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val username = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            Log.d("RegisterActivity", "User input: username=$username, email=$email, password=$password")

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(username, email, password).observe(this) { result ->
                when (result) {
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Log.d("RegisterActivity", "Register response: ${result.data}")
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeay")
                            setMessage("Anda berhasil mendaftar. Sudah siap menjelajah?")
                            setPositiveButton("Lanjut") { _, _ ->
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            }
                            create()
                            show()
                        }
                    }
                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        val error = result.error
                        Log.e("RegisterActivity", "Register error: $error")
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}