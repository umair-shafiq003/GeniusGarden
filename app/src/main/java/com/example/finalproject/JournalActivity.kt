package com.example.finalproject

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class JournalActivity : AppCompatActivity() {

    // Current page number
    private var currentPage = 1

    // Firestore database instance
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journal)

        // Initialize Firestore
        db = Firebase.firestore

        // Get references to UI elements
        val textViewPageNumber = findViewById<TextView>(R.id.textViewPageNumber) // Page indicator
        val textViewDate = findViewById<TextView>(R.id.textViewDate) // Non-editable date display
        val editTextJournalEntry = findViewById<TextView>(R.id.editTextJournalEntry) // Journal entry input
        val buttonAddPage = findViewById<Button>(R.id.buttonAddPage)
        val buttonSubPage = findViewById<Button>(R.id.buttonSubPage)
        val buttonSave = findViewById<Button>(R.id.buttonSave) // Save button
        val buttonBack = findViewById<Button>(R.id.buttonBack) // Back button

        // Set today's date
        setTodayDate(textViewDate)

        // Display initial page number
        updatePageNumber(textViewPageNumber)

        // Add Page button logic
        buttonAddPage.setOnClickListener {
            currentPage++
            editTextJournalEntry.text = ""
            updatePageNumber(textViewPageNumber)
            fetchJournalEntry(currentPage, textViewDate, editTextJournalEntry)
        }

        buttonSubPage.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                fetchJournalEntry(currentPage, textViewDate, editTextJournalEntry)
                updatePageNumber(textViewPageNumber)
            } else {
                Toast.makeText(this, "No previous pages available.", Toast.LENGTH_SHORT).show()
            }
        }

        // Save button logic
        buttonSave.setOnClickListener {
            val date = textViewDate.text.toString().trim()
            val entry = editTextJournalEntry.text.toString().trim()

            if (entry.isNotEmpty()) {
                saveJournalEntry(currentPage, date, entry)
            } else {
                Toast.makeText(this, "Please fill in the journal entry.", Toast.LENGTH_SHORT).show()
            }
        }

        // Back button logic
        buttonBack.setOnClickListener {
            // Navigate back to the previous screen
            finish()
        }

        // Fetch the initial page data
        fetchJournalEntry(currentPage, textViewDate, editTextJournalEntry)
    }

    /**
     * Set today's date in the TextView.
     */
    private fun setTodayDate(dateField: TextView) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        dateField.text = today
    }

    /**
     * Update the page number TextView.
     */
    private fun updatePageNumber(textView: TextView) {
        textView.text = "Page $currentPage"
    }

    /**
     * Save journal entry to Firestore.
     */
    private fun saveJournalEntry(page: Int, date: String, entry: String) {
        val journalData = mapOf(
            "page" to page,
            "date" to date,
            "entry" to entry
        )

        db.collection("journalEntries")
            .document(page.toString()) // Use page number as the document ID
            .set(journalData)
            .addOnSuccessListener {
                Toast.makeText(this, "Journal entry saved!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save journal entry: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    /**
     * Fetch journal entry from Firestore for the given page.
     */
    private fun fetchJournalEntry(page: Int, dateField: TextView, entryField: TextView) {
        db.collection("journalEntries")
            .document(page.toString()) // Fetch by document ID (page number)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val date = document.getString("date") ?: ""
                    val entry = document.getString("entry") ?: ""

                    dateField.text = date
                    entryField.text = entry
                } else {
                    // No entry exists for this page
                    setTodayDate(dateField) // Set today's date for new pages
                    entryField.text = ""
                    Toast.makeText(this, "No journal entry found for page $page.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to fetch journal entry: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}


