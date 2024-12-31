package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText

class SearchActivity : AppCompatActivity() {

    private lateinit var adapter: PlantAdapter
    private lateinit var allPlants: List<Plant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // List of plants (you can retrieve this list dynamically if needed)
        allPlants = listOf(
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

        // Set up RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = PlantAdapter(allPlants) { plantName ->
            val intent = Intent(this, PlantDetailActivity::class.java).apply {
                putExtra(PlantDetailActivity.EXTRA_PLANT_NAME, plantName)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up Search input
        val searchEditText: EditText = findViewById(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPlants(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterPlants(query: String) {
        val filteredPlants = allPlants.filter { it.name.contains(query, ignoreCase = true) }
        adapter = PlantAdapter(filteredPlants) { plantName ->
            val intent = Intent(this, PlantDetailActivity::class.java).apply {
                putExtra(PlantDetailActivity.EXTRA_PLANT_NAME, plantName)
            }
            startActivity(intent)
        }
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
    }
}
