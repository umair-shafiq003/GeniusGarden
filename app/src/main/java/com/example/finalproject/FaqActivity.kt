package com.example.finalproject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FaqActivity : AppCompatActivity() {

    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        backButton =  findViewById(R.id.backButton)

        backButton.setOnClickListener {
            // Navigate back to the previous screen
            finish()
        }
    }
}
