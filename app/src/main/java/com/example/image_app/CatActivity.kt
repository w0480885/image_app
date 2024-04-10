package com.example.image_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.image_app.databinding.ActivityCatBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import androidx.recyclerview.widget.LinearLayoutManager



class CatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatBinding
    private val catImages = mutableListOf<CatImage>()
    private lateinit var catAdapter: CatAdapter
    private var isLoading = false
    private var page = 0 // Keep track of which page to load next

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchCatImages()
    }

    private fun setupRecyclerView() {
        catAdapter = CatAdapter(catImages)
        val layoutManager = LinearLayoutManager(this) // Changed to LinearLayoutManager
        binding.catRecyclerView.layoutManager = layoutManager
        binding.catRecyclerView.adapter = catAdapter

        binding.catRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val threshold = 2 // How many items to have below your current scroll position before loading more.

                if (!isLoading && totalItemCount <= (lastVisibleItemPosition + threshold)) {
                    page++ // Increase the page index to load the next set of items.
                    fetchCatImages()
                    isLoading = true
                }
            }
        })
    }

    private fun fetchCatImages() {
        val catApiService = getRetrofit().create(CatApiService::class.java)
        catApiService.getCatImages(limit = 10, page = page).enqueue(object : Callback<List<CatImage>> {
            override fun onResponse(call: Call<List<CatImage>>, response: Response<List<CatImage>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.isNotEmpty()) {
                            catImages.addAll(it)
                            catAdapter.notifyDataSetChanged()
                            isLoading = false // Reset loading state
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CatImage>>, t: Throwable) {
                isLoading = false // Reset loading state
            }
        })
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    interface CatApiService {
        @GET("v1/images/search")
        fun getCatImages(@Query("limit") limit: Int, @Query("page") page: Int): Call<List<CatImage>>
    }
}






