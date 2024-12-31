package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Data class for Plant
data class Plant(
    val name: String,
    val description: String,
    val imageResId: Int
)

// RecyclerView Adapter to display plants in a list
class PlantAdapter(
    private val plants: List<Plant>,
    private val onPlantClick: (String) -> Unit // Add a lambda for click handling
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plant_item, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plants[position]
        holder.bind(plant)
        // Set up click listener
        holder.itemView.setOnClickListener { onPlantClick(plant.name) }
    }

    override fun getItemCount(): Int {
        return plants.size
    }

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val plantImageView: ImageView = itemView.findViewById(R.id.plantImageView)
        private val plantDescriptionTextView: TextView = itemView.findViewById(R.id.plantDescriptionTextView)

        fun bind(plant: Plant) {
            titleTextView.text = plant.name
            plantImageView.setImageResource(plant.imageResId)
            plantDescriptionTextView.text = plant.description
        }
    }
}
