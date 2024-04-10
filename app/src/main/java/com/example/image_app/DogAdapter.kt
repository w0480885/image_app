package com.example.image_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.image_app.R

class DogAdapter(private val dogImages: MutableList<String>) : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    class DogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.dogImageView)

        fun bind(imageUrl: String) {
            Glide.with(itemView.context).load(imageUrl).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(dogImages[position])
    }

    override fun getItemCount() = dogImages.size

    fun addDogImages(images: List<String>) {
        dogImages.addAll(images)
        notifyDataSetChanged()
    }
}
