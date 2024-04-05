package com.example.image_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.image_app.databinding.ItemCatBinding

class CatAdapter(private val catImages: List<CatImage>) :
    RecyclerView.Adapter<CatAdapter.CatViewHolder>() {

    class CatViewHolder(private val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(catImage: CatImage) {
            Glide.with(binding.catImageView.context).load(catImage.url).into(binding.catImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(catImages[position])
    }

    override fun getItemCount(): Int = catImages.size
}
