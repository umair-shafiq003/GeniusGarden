package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HerbPlantsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_herb_plants)

        // List of plants
        val plants = listOf(
            Plant("Basil", "Basil is a popular herb used in culinary dishes.", R.drawable.herb_basil),
            Plant("Mint", "Mint is a refreshing herb often used in beverages.", R.drawable.herb_mint),
            Plant("Rosemary", "Rosemary is a fragrant herb perfect for cooking and aromatherapy.", R.drawable.herb_rosemary),
            Plant("Thyme", "Thyme is a versatile herb used in many savory dishes.", R.drawable.herb_thyme),
            Plant("Oregano", "Oregano is a staple herb in Mediterranean cuisine.", R.drawable.herb_oregano),
            Plant("Parsley", "Parsley is a fresh herb often used as a garnish or seasoning.", R.drawable.herb_parsley),
            Plant("Cilantro", "Cilantro is a flavorful herb used in many global dishes.", R.drawable.herb_cilantro),
            Plant("Chives", "Chives are a mild-flavored herb often added to soups and salads.", R.drawable.herb_chives),
            Plant("Sage", "Sage is an aromatic herb used in cooking and teas.", R.drawable.herb_sage),
            Plant("Dill", "Dill is a fragrant herb commonly used in pickling.", R.drawable.herb_dill),
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
