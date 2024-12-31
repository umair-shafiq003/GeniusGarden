package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailAddressEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var backButton: Button
    private lateinit var createAccountButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createaccount)

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        // Initialize the views
        firstNameEditText = findViewById(R.id.firstName)
        lastNameEditText = findViewById(R.id.lastName)
        emailAddressEditText = findViewById(R.id.emailAddress)
        countryEditText = findViewById(R.id.country)
        passwordEditText = findViewById(R.id.password)
        confirmPasswordEditText = findViewById(R.id.confirmPassword)
        backButton = findViewById(R.id.backButton)
        createAccountButton = findViewById(R.id.createAccountButton)
        progressBar = findViewById(R.id.progressBar)

        progressBar.visibility = View.GONE

        // Set up button click listeners
        backButton.setOnClickListener {
            finish()
        }

        createAccountButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            registerNewUser()
        }
    }

    private fun registerNewUser() {
        progressBar.visibility = View.VISIBLE

        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val email = emailAddressEditText.text.toString().trim()
        val country = countryEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Basic input validation
        if (TextUtils.isEmpty(firstName)) {
            firstNameEditText.error = "Please enter first name"
            progressBar.visibility = View.GONE
            return
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameEditText.error = "Please enter last name"
            progressBar.visibility = View.GONE
            return
        }

        if (TextUtils.isEmpty(email)) {
            emailAddressEditText.error = "Please enter email"
            progressBar.visibility = View.GONE
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddressEditText.error = "Please enter a valid email address"
            progressBar.visibility = View.GONE
            return
        }

        if (TextUtils.isEmpty(country)) {
            countryEditText.error = "Please enter country"
            progressBar.visibility = View.GONE
            return
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.error = "Please enter password"
            progressBar.visibility = View.GONE
            return
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.error = "Please confirm password"
            progressBar.visibility = View.GONE
            return
        }

        if (password.length < 6) {
            passwordEditText.error = "Password must be at least 6 characters"
            progressBar.visibility = View.GONE
            return
        }

        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Passwords do not match"
            progressBar.visibility = View.GONE
            return
        }

        // Register new user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Get the current user reference
                    val user = mAuth.currentUser
                    val uid = user?.uid

                    // Create a map to store user data
                    val userData = hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                        "country" to country
                    )

                    // Add user data to Firestore
                    uid?.let {
                        db.collection("users").document(it)
                            .set(userData, SetOptions.merge())
                            .addOnSuccessListener {
                                progressBar.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    "Registration successful!",
                                    Toast.LENGTH_LONG
                                ).show()

                                // Redirect to home activity after successful registration
                                val intent = Intent(this, DashboardActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                progressBar.visibility = View.GONE
                                Toast.makeText(
                                    applicationContext,
                                    "Registration successful, but failed to add user data to Firestore.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }
                } else {
                    // Registration failed
                    progressBar.visibility = View.GONE
                    var errorMessage = "Registration failed."

                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthException) {
                        when (e.errorCode) {
                            "weak-password" -> errorMessage = "Password is too weak."
                            "email-already-in-use" -> errorMessage = "Email address is already in use."
                            // Handle other error codes as needed
                        }
                    }

                    Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }
}