package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SucculentPlantsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_succulent_plants)

        // List of plants
        val plants = listOf(
            Plant("Aloe Vera", "Aloe Vera is widely used for its medicinal properties.", R.drawable.succulent_aloe),
            Plant("Jade Plant", "Jade Plant is a symbol of good luck and prosperity.", R.drawable.succulent_jade_plant),
            Plant("Echeveria", "Echeveria is a rosette-forming succulent with vibrant colors.", R.drawable.succulent_echeveria),
            Plant("String of Pearls", "String of Pearls is a trailing succulent with bead-like leaves.", R.drawable.succulent_string_of_pearls),
            Plant("Zebra Haworthia", "Zebra Haworthia is a compact succulent with striped leaves.", R.drawable.succulent_zebra_haworthia),
            Plant("Burro's Tail", "Burro's Tail is a cascading succulent with plump leaves.", R.drawable.succulent_burros_tail),
            Plant("Agave", "Agave is a striking succulent with spiky, architectural leaves.", R.drawable.succulent_agave),
            Plant("Lithops", "Lithops, also known as 'living stones,' resemble small pebbles.", R.drawable.succulent_lithops),
            Plant("Christmas Cactus", "Christmas Cactus blooms around the holiday season.", R.drawable.succulent_christmas_cactus),
            Plant("Kalanchoe", "Kalanchoe is a flowering succulent available in various colors.", R.drawable.succulent_kalanchoe),
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

