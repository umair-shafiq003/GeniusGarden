package com.example.finalproject

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class ChatMessage(val message: String, val isUser: Boolean)


class LiveChatActivity : AppCompatActivity() {

    private val chatMessages = mutableListOf<ChatMessage>()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_chat)

        val messageInput: EditText = findViewById(R.id.messageInput)
        val sendButton: Button = findViewById(R.id.sendButton)
        val chatRecyclerView: RecyclerView = findViewById(R.id.chatRecyclerView)

        adapter = ChatAdapter(chatMessages)
        chatRecyclerView.adapter = adapter
        chatRecyclerView.layoutManager = LinearLayoutManager(this)

        sendButton.setOnClickListener {
            val userMessage = messageInput.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                addMessage(ChatMessage(userMessage, isUser = true))
                messageInput.text.clear()

                // Simulate bot response
                val botResponse = getBotResponse(userMessage)
                addMessage(ChatMessage(botResponse, isUser = false))
            }
        }
    }

    private fun addMessage(message: ChatMessage) {
        chatMessages.add(message)
        adapter.notifyItemInserted(chatMessages.size - 1)
        findViewById<RecyclerView>(R.id.chatRecyclerView).scrollToPosition(chatMessages.size - 1)
    }

    private fun getBotResponse(userMessage: String): String {
        return when {
            userMessage.contains("hello", ignoreCase = true) -> {
                "Hi there! How can I assist you today? Here are some options:\n" +
                        "1. Ask about a plant (e.g., 'Tell me about Snake Plant')\n" +
                        "2. Ask for a list of plants\n" +
                        "3. Get plant care tips"
            }
            userMessage.contains("list plants", ignoreCase = true) -> {
                "Here are some plants you can ask about:\n" +
                        "1. Snake Plant\n" +
                        "2. Spider Plant\n" +
                        "3. Peace Lily\n" +
                        "4. Fiddle Leaf Fig\n" +
                        "5. Pothos\n" +
                        "6. Monstera\n" +
                        "7. ZZ Plant\n" +
                        "8. Lavender\n" +
                        "9. Rose\n" +
                        "(Type the name of any plant to know more!)"
            }
            userMessage.contains("care tips", ignoreCase = true) -> {
                "To care for your plants, follow these general tips:\n" +
                        "1. Ensure proper sunlight\n" +
                        "2. Water regularly, but not too much\n" +
                        "3. Use good quality soil\n" +
                        "4. Avoid overfeeding your plants with fertilizers\n" +
                        "Would you like care tips for a specific plant?"
            }
            userMessage.contains("snake plant", ignoreCase = true) -> {
                getPlantInfo("Snake Plant")
            }
            userMessage.contains("spider plant", ignoreCase = true) -> {
                getPlantInfo("Spider Plant")
            }
            userMessage.contains("peace lily", ignoreCase = true) -> {
                getPlantInfo("Peace Lily")
            }
            userMessage.contains("fiddle leaf fig", ignoreCase = true) -> {
                getPlantInfo("Fiddle Leaf Fig")
            }
            userMessage.contains("pothos", ignoreCase = true) -> {
                getPlantInfo("Pothos")
            }
            userMessage.contains("monstera", ignoreCase = true) -> {
                getPlantInfo("Monstera")
            }
            userMessage.contains("zz plant", ignoreCase = true) -> {
                getPlantInfo("ZZ Plant")
            }
            userMessage.contains("lavender", ignoreCase = true) -> {
                getPlantInfo("Lavender")
            }
            userMessage.contains("rose", ignoreCase = true) -> {
                getPlantInfo("Rose")
            }
            else -> {
                "Sorry, I didn't quite catch that. You can ask for a list of plants or request information about a specific plant."
            }
        }
    }

    // Function to return plant information based on its name
    private fun getPlantInfo(plantName: String): String {
        return when (plantName) {
            "Snake Plant" -> "Snake Plant is a hardy indoor plant known for improving air quality."
            "Spider Plant" -> "Spider Plant is a low-maintenance plant with air-purifying qualities."
            "Peace Lily" -> "Peace Lily is a beautiful flowering plant that purifies the air."
            "Fiddle Leaf Fig" -> "Fiddle Leaf Fig is a popular indoor tree with large, glossy leaves."
            "Pothos" -> "Pothos is a versatile vine plant that's easy to grow indoors."
            "Monstera" -> "Monstera is known for its large, perforated leaves."
            "ZZ Plant" -> "ZZ Plant is an incredibly low-maintenance indoor plant."
            "Lavender" -> "Lavender is a fragrant outdoor plant popular for its calming scent."
            "Rose" -> "Rose is a classic flowering plant available in numerous varieties."
            else -> "I'm not sure about that plant. Please check the plant list for more options."
        }
    }

}
