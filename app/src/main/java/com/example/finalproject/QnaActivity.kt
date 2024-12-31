package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class QnaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna)

        // FAQ Button
        val faqButton: ImageButton = findViewById(R.id.faqButton)
        faqButton.setOnClickListener {
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
        }

        // Live Chat Button
        val liveChatButton: ImageButton = findViewById(R.id.liveChatButton)
        liveChatButton.setOnClickListener {
            val intent = Intent(this, LiveChatActivity::class.java)
            startActivity(intent)
        }
    }
}
