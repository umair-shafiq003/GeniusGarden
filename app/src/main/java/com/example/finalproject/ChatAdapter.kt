package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: List<ChatMessage>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userMessageView: TextView = itemView.findViewById(R.id.userMessageView)
        private val botMessageView: TextView = itemView.findViewById(R.id.botMessageView)

        fun bind(message: ChatMessage) {
            if (message.isUser) {
                userMessageView.visibility = View.VISIBLE
                botMessageView.visibility = View.GONE
                userMessageView.text = message.message
            } else {
                userMessageView.visibility = View.GONE
                botMessageView.visibility = View.VISIBLE
                botMessageView.text = message.message
            }
        }
    }
}
