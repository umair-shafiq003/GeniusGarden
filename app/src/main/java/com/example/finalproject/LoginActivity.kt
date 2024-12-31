package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    private lateinit var forgotPasswordLink: TextView  // Added Forgot Password link

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Find the views
        emailEditText = findViewById(R.id.emailField)
        passwordEditText = findViewById(R.id.passwordField)
        loginButton = findViewById(R.id.nextButton)
        progressBar = findViewById(R.id.progressBar)
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink)  // Initialize the forgot password link

        progressBar.visibility = View.GONE

        // Set click listener for login button
        loginButton.setOnClickListener {
            userLogin()
        }

        // Set click listener for forgot password link
        forgotPasswordLink.setOnClickListener {
            handleForgotPassword()
        }
    }

    private fun userLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            emailEditText.error = "Please enter email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email address"
            return
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.error = "Please enter password"
            return
        }

        progressBar.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Login successful!", Toast.LENGTH_LONG).show()

                    // Navigate to DashboardActivity after successful login
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(applicationContext, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun handleForgotPassword() {
        val email = emailEditText.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            emailEditText.error = "Please enter your email address"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email address"
            return
        }

        progressBar.visibility = View.VISIBLE

        // Send password reset email
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Password reset email sent", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
