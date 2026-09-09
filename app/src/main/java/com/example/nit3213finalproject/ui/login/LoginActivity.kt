package com.example.nit3213finalproject.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nit3213finalproject.databinding.ActivityLoginBinding
import com.example.nit3213finalproject.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        observeViewModel()
    }

    private fun setupClickListeners() {

        binding.btnLogin.setOnClickListener {

            val studentId =
                binding.etUsername.text
                    ?.toString()
                    ?.trim()
                    .orEmpty()

            val firstName =
                binding.etPassword.text
                    ?.toString()
                    ?.trim()
                    .orEmpty()

            binding.usernameLayout.error = null
            binding.passwordLayout.error = null
            binding.tvError.visibility = View.GONE

            var valid = true

            if (studentId.isEmpty()) {

                binding.usernameLayout.error =
                    "Student ID is required."

                valid = false

            } else if (!studentId.matches(Regex("^\\d+$"))) {

                binding.usernameLayout.error =
                    "Student ID must contain numbers only, without 's'."

                valid = false
            }

            if (firstName.isEmpty()) {

                binding.passwordLayout.error =
                    "First name is required."

                valid = false
            }

            if (valid) {

                viewModel.login(
                    studentId = studentId,
                    firstName = firstName
                )
            }
        }
    }

    private fun observeViewModel() {

        viewModel.loading.observe(this) { isLoading ->

            binding.progressBar.visibility =
                if (isLoading) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

            binding.btnLogin.isEnabled = !isLoading
        }

        viewModel.error.observe(this) { errorMessage ->

            if (errorMessage.isNullOrBlank()) {

                binding.tvError.visibility =
                    View.GONE

            } else {

                binding.tvError.text =
                    errorMessage

                binding.tvError.visibility =
                    View.VISIBLE
            }
        }

        viewModel.keypass.observe(this) { keypass ->

            if (!keypass.isNullOrBlank()) {

                val intent = Intent(
                    this,
                    DashboardActivity::class.java
                )

                intent.putExtra(
                    DashboardActivity.EXTRA_KEYPASS,
                    keypass
                )

                startActivity(intent)
                finish()
            }
        }
    }
}