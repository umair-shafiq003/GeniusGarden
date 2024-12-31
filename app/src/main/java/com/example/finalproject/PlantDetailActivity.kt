package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class PlantDetailActivity : AppCompatActivity() {

    private lateinit var plantImageView: ImageView
    private lateinit var plantDescriptionTextView: TextView
    private lateinit var stepsToPlantTextView: TextView
    private lateinit var instructionsToWaterTextView: TextView

    private val plantImageMap = mapOf(
        "Snake Plant" to R.drawable.indoor_snake_plant,
        "Spider Plant" to R.drawable.indoor_spider_plant,
        "Peace Lily" to R.drawable.indoor_peace_lily,
        "Fiddle Leaf Fig" to R.drawable.indoor_fiddle_leaf_fig,
        "Pothos" to R.drawable.indoor_pothos,
        "Monstera" to R.drawable.indoor_monstera,
        "ZZ Plant" to R.drawable.indoor_zz_plant,
        "Chinese Evergreen" to R.drawable.indoor_chinese_evergreen,
        "Rubber Plant" to R.drawable.indoor_rubber_plant,
        "Parlor Palm" to R.drawable.indoor_parlor_palm,
        "Lavender" to R.drawable.outdoor_lavender,
        "Hibiscus" to R.drawable.outdoor_hibiscus,
        "Sunflower" to R.drawable.outdoor_sunflower,
        "Rose" to R.drawable.outdoor_rose,
        "Bougainvillea" to R.drawable.outdoor_bougainvillea,
        "Marigold" to R.drawable.outdoor_marigold,
        "Jasmine" to R.drawable.outdoor_jasmine,
        "Bamboo" to R.drawable.outdoor_bamboo,
        "Hydrangea" to R.drawable.outdoor_hydrangea,
        "Geranium" to R.drawable.outdoor_geranium,
        "Basil" to R.drawable.herb_basil,
        "Mint" to R.drawable.herb_mint,
        "Rosemary" to R.drawable.herb_rosemary,
        "Thyme" to R.drawable.herb_thyme,
        "Oregano" to R.drawable.herb_oregano,
        "Parsley" to R.drawable.herb_parsley,
        "Cilantro" to R.drawable.herb_cilantro,
        "Chives" to R.drawable.herb_chives,
        "Sage" to R.drawable.herb_sage,
        "Dill" to R.drawable.herb_dill,
        "Aloe Vera" to R.drawable.succulent_aloe,
        "Jade Plant" to R.drawable.succulent_jade_plant,
        "Echeveria" to R.drawable.succulent_echeveria,
        "String of Pearls" to R.drawable.succulent_string_of_pearls,
        "Zebra Haworthia" to R.drawable.succulent_zebra_haworthia,
        "Burro's Tail" to R.drawable.succulent_burros_tail,
        "Agave" to R.drawable.succulent_agave,
        "Lithops" to R.drawable.succulent_lithops,
        "Christmas Cactus" to R.drawable.succulent_christmas_cactus,
        "Kalanchoe" to R.drawable.succulent_kalanchoe

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        // Initialize views
        plantImageView = findViewById(R.id.plantImage)
        plantDescriptionTextView = findViewById(R.id.plantDescription)
        stepsToPlantTextView = findViewById(R.id.stepsToPlant)
        instructionsToWaterTextView = findViewById(R.id.instructionsToWater)

        // Get plant name from intent
        val plantName = intent.getStringExtra(EXTRA_PLANT_NAME) ?: ""

        if (plantName.isNotEmpty()) {
            fetchPlantDetails(plantName)
        } else {
            Log.w("PlantDetailActivity", "No plant name provided in the intent.")
            // Handle the error case (e.g., display a default message or finish the activity)
        }
    }

    private fun fetchPlantDetails(plantName: String) {
        val db = FirebaseFirestore.getInstance()
        val plantsCollection: CollectionReference = db.collection("plants")
        val query: Query = plantsCollection.whereEqualTo("name", plantName)

        query.get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (!result.isEmpty) {
                        for (document in result) {
                            val plantDescription = document.getString("description") ?: ""
                            val plantingSteps = document.getString("growSteps") ?: ""
                            val wateringInstructions = document.getString("waterSteps") ?: ""

                            updatePlantDetails(plantDescription, plantingSteps, wateringInstructions)
                        }
                    } else {
                        Log.w("PlantDetailActivity", "No plant details found for the given name.")
                        // Handle case where no matching plant is found
                    }
                } else {
                    Log.w("PlantDetailActivity", "Error fetching plant details", task.exception)
                    // Handle the error case
                }
            }
    }

    private fun updatePlantDetails(plantDescription: String, plantingSteps: String, wateringInstructions: String) {
        plantDescriptionTextView.text = plantDescription
        stepsToPlantTextView.text = plantingSteps
        instructionsToWaterTextView.text = wateringInstructions

        val plantName = intent.getStringExtra(EXTRA_PLANT_NAME) ?: ""


        val imageResourceId = plantImageMap[plantName] ?: R.drawable.main_header
        // Use placeholder if not found
        plantImageView.setImageResource(imageResourceId)
    }

    companion object {
        // Updated key to match intent extra usage
        const val EXTRA_PLANT_NAME = "extra_plant_name"
    }
}