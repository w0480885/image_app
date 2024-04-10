package com.example.image_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.image_app.R

class DogActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog)

        recyclerView = findViewById(R.id.dogRecyclerView)
        adapter = DogAdapter(dogImages)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Infinite scroll listener
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    fetchRandomDogImages() // Updated to the new method name
                }
            }
        })


        fetchRandomDogImages()
    }

    private fun fetchRandomDogImages() {
        DogApiService.instance.getRandomDogImage().enqueue(object : Callback<DogImage> {
            override fun onResponse(call: Call<DogImage>, response: Response<DogImage>) {
                response.body()?.let { dogImagesResponse ->
                    dogImages.addAll(dogImagesResponse.message) // Add all fetched URLs to the list
                    adapter.notifyDataSetChanged() // Notify the adapter that the data set has changed
                }
            }

            override fun onFailure(call: Call<DogImage>, t: Throwable) {
                // Handle failure
            }
        })
    }

}


