package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Get a reference to Firebase Auth
        val mAuth = FirebaseAuth.getInstance()

        // Check if a user is logged in
        val currentUser = mAuth.currentUser

        // If a user is logged in, get their email address
        val loggedInUserEmail = currentUser?.email ?: ""

        // Extract the first initial from the email address
        val userInitial = if (loggedInUserEmail.isNotEmpty()) loggedInUserEmail.first().toString().uppercase() else "?"

        // Set the initial in the TextView
        val userInitialsTextView: TextView = findViewById(R.id.userInitials)
        userInitialsTextView.text = userInitial

        // Get references to the circular buttons
        val sectionOneButton: ImageButton = findViewById(R.id.sectionOneButton)
        val sectionTwoButton: ImageButton = findViewById(R.id.sectionTwoButton)
        val sectionThreeButton: ImageButton = findViewById(R.id.sectionThreeButton)
        val sectionFourButton: ImageButton = findViewById(R.id.sectionFourButton)

        // Get references to the ImageView and TextView elements in the categories
        val indoorPlantsImage: ImageView = findViewById(R.id.indoorPlantsImage)

        val outdoorPlantsImage: ImageView = findViewById(R.id.outdoorPlantsImage)

        val herbsImage: ImageView = findViewById(R.id.herbsImage)

        val succulentsImage: ImageView = findViewById(R.id.succulentsImage)

        // Handle click events for the circular buttons
        sectionOneButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        sectionTwoButton.setOnClickListener {
            startActivity(Intent(this, QnaActivity::class.java))
        }

        sectionThreeButton.setOnClickListener {
            startActivity(Intent(this, JournalActivity::class.java))
        }

        sectionFourButton.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }
        // Handle click events for the categories
        indoorPlantsImage.setOnClickListener {
            startActivity(Intent(this, IndoorPlantsActivity::class.java))
        }

        outdoorPlantsImage.setOnClickListener {
            startActivity(Intent(this, OutdoorPlantsActivity::class.java))
        }

        herbsImage.setOnClickListener {
            startActivity(Intent(this, HerbPlantsActivity::class.java))
        }


        succulentsImage.setOnClickListener {
            startActivity(Intent(this, SucculentPlantsActivity::class.java))
        }

        userInitialsTextView.setOnClickListener {
            startActivity(Intent(this, ProfileSettingsActivity::class.java))
        }

    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        count++
        if (count < 2){
            Toast.makeText(this, "Press Again To Exit App", Toast.LENGTH_SHORT).show()
        }
        else{Toast.makeText(this, "APP EXITED", Toast.LENGTH_SHORT).show()
            finishAffinity()
        }
    }
}
