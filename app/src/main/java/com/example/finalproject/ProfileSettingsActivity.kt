package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileSettingsActivity : AppCompatActivity() {

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailAddressEditText: EditText
    private lateinit var countryEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var backButton: Button
    private lateinit var logoutButton: Button
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val currentUser = FirebaseAuth.getInstance().currentUser

        // If a user is logged in, get their email address
        val loggedInUserEmail = currentUser?.email ?: ""

        // Extract the first initial from the email address
        val userInitial = if (loggedInUserEmail.isNotEmpty()) loggedInUserEmail.first().toString().uppercase() else "?"

        // Set the initial in the TextView
        val userInitialsTextView: TextView = findViewById(R.id.userInitials)
        userInitialsTextView.text = userInitial

        firstNameEditText = findViewById(R.id.firstName)
        lastNameEditText = findViewById(R.id.lastName)
        emailAddressEditText = findViewById(R.id.emailAddress)
        countryEditText = findViewById(R.id.country)
        updateButton = findViewById(R.id.updateButton)
        backButton = findViewById(R.id.backButton)
        logoutButton = findViewById(R.id.logoutButton)
        userName = findViewById(R.id.userName)
        userEmail = findViewById(R.id.userEmail)

        // Mock data (replace with actual data retrieval logic)
        loadUserData()

        // Set button listeners
        updateButton.setOnClickListener {
            updateUserProfile()
        }

        backButton.setOnClickListener {
            finish()
        }

        // Logout button listener
        logoutButton.setOnClickListener {
            logoutUser()
        }
    }

    private fun loadUserData() {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val uid = currentUser.uid

            // Get the user document reference
            val userDocRef = db.collection("users").document(uid)

            userDocRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val firstName = documentSnapshot.getString("firstName") ?: ""
                        val lastName = documentSnapshot.getString("lastName") ?: ""
                        val email = currentUser.email ?: ""
                        val country = documentSnapshot.getString("country") ?: ""

                        firstNameEditText.setText(firstName)
                        lastNameEditText.setText(lastName)
                        emailAddressEditText.setText(email)
                        countryEditText.setText(country)
                        "$firstName $lastName".also { userName.text = it }
                        userEmail.text = email
                    } else {
                        Toast.makeText(this, "User document not found", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error getting user document: ${it.message}", Toast.LENGTH_LONG).show()
                }
        } else {
            firstNameEditText.setText("")
            lastNameEditText.setText("")
            emailAddressEditText.setText("")
            countryEditText.setText("")
        }
    }

    private fun updateUserProfile() {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val email = emailAddressEditText.text.toString().trim()
        val country = countryEditText.text.toString().trim()

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || country.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
    }

    private fun logoutUser() {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()

        // Redirect to login screen or close the activity
        val intent = Intent(this, LoginActivity::class.java) // Replace LoginActivity with your actual login activity
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
