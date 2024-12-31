package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OutdoorPlantsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outdoor_plants)

        // List of plants
        val plants = listOf(
            Plant("Lavender", "Lavender is a fragrant outdoor plant popular for its calming scent.", R.drawable.outdoor_lavender),
            Plant("Hibiscus", "Hibiscus produces vibrant, showy flowers in various colors.", R.drawable.outdoor_hibiscus),
            Plant("Sunflower", "Sunflower is known for its large, bright yellow blooms.", R.drawable.outdoor_sunflower),
            Plant("Rose", "Rose is a classic flowering plant available in numerous varieties.", R.drawable.outdoor_rose),
            Plant("Bougainvillea", "Bougainvillea is a vibrant climber plant suited for outdoor spaces.", R.drawable.outdoor_bougainvillea),
            Plant("Marigold", "Marigold is a hardy flower that thrives in sunlight.", R.drawable.outdoor_marigold),
            Plant("Jasmine", "Jasmine is known for its fragrant, star-shaped flowers.", R.drawable.outdoor_jasmine),
            Plant("Bamboo", "Bamboo is a fast-growing plant perfect for creating privacy outdoors.", R.drawable.outdoor_bamboo),
            Plant("Hydrangea", "Hydrangea produces large, colorful flower clusters.", R.drawable.outdoor_hydrangea),
            Plant("Geranium", "Geranium is an outdoor favorite with bright, cheerful flowers.", R.drawable.outdoor_geranium),
        )

        val adapter = PlantAdapter(plants) { plantName ->
            // Start PlantDetailActivity with the plant name as an extra
            val intent = Intent(this, PlantDetailActivity::class.java).apply {
                putExtra(PlantDetailActivity.EXTRA_PLANT_NAME, plantName)
            }
            startActivity(intent)
        }

// Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
