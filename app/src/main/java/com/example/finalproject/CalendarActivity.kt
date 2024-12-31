package com.example.finalproject

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var selectedDateText: TextView
    private lateinit var reminderInput: EditText
    private lateinit var addReminderButton: Button
    private lateinit var saveReminderButton: Button
    private lateinit var reminderList: TextView
    private lateinit var progressBar: ProgressBar

    private val remindersMap = mutableMapOf<String, MutableList<String>>()
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Initialize views
        calendarView = findViewById(R.id.calendarView)
        selectedDateText = findViewById(R.id.selectedDateText)
        reminderInput = findViewById(R.id.reminderInput)
        addReminderButton = findViewById(R.id.addReminderButton)
        saveReminderButton = findViewById(R.id.saveReminderButton)
        reminderList = findViewById(R.id.reminderList)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE

        // Initialize Firestore
        db = Firebase.firestore

        // Set a listener for the calendar
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(year - 1900, month, dayOfMonth))
            selectedDateText.text = "Selected Date: $selectedDate"

            // Fetch reminders from Firestore for the selected date
            fetchReminders(selectedDate)

            addReminderButton.isEnabled = true
        }

        // Add reminder button
        addReminderButton.setOnClickListener {
            val reminderText = reminderInput.text.toString().trim()
            if (reminderText.isNotEmpty()) {
                val selectedDate = selectedDateText.text.toString().replace("Selected Date: ", "")
                saveReminderToFirestore(selectedDate, reminderText)
                reminderInput.text.clear()
            } else {
                Toast.makeText(this, "Please enter a reminder", Toast.LENGTH_SHORT).show()
            }
        }

        // Save reminders button (dummy progress example)
        saveReminderButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            Toast.makeText(this, "All reminders saved!", Toast.LENGTH_SHORT).show()
            progressBar.visibility = View.GONE
        }
    }

    private fun saveReminderToFirestore(date: String, reminder: String) {
        val reminderData = mapOf(
            "date" to date,
            "reminder" to reminder
        )

        db.collection("reminders")
            .add(reminderData)
            .addOnSuccessListener {
                Toast.makeText(this, "Reminder saved!", Toast.LENGTH_SHORT).show()
                fetchReminders(date) // Update reminders list
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save reminder", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchReminders(date: String) {
        progressBar.visibility = View.VISIBLE
        db.collection("reminders")
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { documents ->
                val reminders = mutableListOf<String>()
                for (document in documents) {
                    val reminder = document.getString("reminder") ?: ""
                    reminders.add(reminder)
                }
                remindersMap[date] = reminders
                showReminders(date)
                progressBar.visibility = View.GONE
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to fetch reminders", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
    }

    private fun showReminders(date: String) {
        if (remindersMap.containsKey(date)) {
            val reminders = remindersMap[date]!!
            val reminderText = StringBuilder("Reminders for $date:\n")
            for (reminder in reminders) {
                reminderText.append("- $reminder\n")
            }
            reminderList.text = reminderText.toString()
        } else {
            reminderList.text = "No reminders for $date."
        }
    }
}
