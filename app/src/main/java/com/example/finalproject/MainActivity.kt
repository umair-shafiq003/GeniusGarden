package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton: Button = findViewById(R.id.loginButton)
        val createAccountButton: Button = findViewById(R.id.createAccountButton)

        // Handle button clicks
        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        createAccountButton.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
    }
}