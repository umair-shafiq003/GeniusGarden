package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class IndoorPlantsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor_plants)

        // List of plants
        val plants = listOf(
            Plant("Snake Plant", "Snake Plant is a hardy indoor plant known for improving air quality.", R.drawable.indoor_snake_plant),
            Plant("Spider Plant", "Spider Plant is a low-maintenance plant with air-purifying qualities.", R.drawable.indoor_spider_plant),
            Plant("Peace Lily", "Peace Lily is a beautiful flowering plant that purifies the air.", R.drawable.indoor_peace_lily),
            Plant("Fiddle Leaf Fig", "Fiddle Leaf Fig is a popular indoor tree with large, glossy leaves.", R.drawable.indoor_fiddle_leaf_fig),
            Plant("Pothos", "Pothos is a versatile vine plant that's easy to grow indoors.", R.drawable.indoor_pothos),
            Plant("Monstera", "Monstera is known for its large, perforated leaves.", R.drawable.indoor_monstera),
            Plant("ZZ Plant", "ZZ Plant is an incredibly low-maintenance indoor plant.", R.drawable.indoor_zz_plant),
            Plant("Chinese Evergreen", "Chinese Evergreen is a colorful, easy-to-care-for houseplant.", R.drawable.indoor_chinese_evergreen),
            Plant("Rubber Plant", "Rubber Plant has thick, glossy leaves and adds greenery indoors.", R.drawable.indoor_rubber_plant),
            Plant("Parlor Palm", "Parlor Palm is a classic indoor palm that thrives in low light.", R.drawable.indoor_parlor_palm),
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
        recyclerView.layoutManager = LinearLayoutManager(this)}
}
