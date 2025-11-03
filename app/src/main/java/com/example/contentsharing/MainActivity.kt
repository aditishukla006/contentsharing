package com.example.contentsharingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.contentsharingapp.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tileList = mutableListOf<Tile>()
    private lateinit var adapter: TileAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TileAdapter(tileList)
        binding.recyclerView?.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView?.adapter = adapter

        loadTilesFromFirebase()
    }

    private fun loadTilesFromFirebase() {
        db.collection("contentTiles")
            .get()
            .addOnSuccessListener { documents ->
                tileList.clear()
                for (doc in documents) {
                    val title = doc.getString("title") ?: ""
                    val imageUrl = doc.getString("ImageUrl") ?: ""
                    val webUrl = doc.getString("WebUrl") ?: ""

                    tileList.add(Tile(title, imageUrl,webUrl))
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

}
