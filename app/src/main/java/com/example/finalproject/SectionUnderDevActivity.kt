package com.example.finalproject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SectionUnderDevActivity : AppCompatActivity() {

    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sectiononeactivity)

        backButton = findViewById(R.id.backButton)


        // Set up the back button in your layout
        backButton.setOnClickListener {
            // Simulate back button press
            onBackPressed()
        }
    }
}
