package com.example.recyclerwithscreentransition.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.recyclerwithscreentransition.databinding.ItemRecipeBinding
import com.example.recyclerwithscreentransition.ui.model.Recipe
import kotlin.math.roundToInt

class HorizontalRecipeAdapter(private val onItemClick: (Recipe, View) -> Unit): RecyclerView.Adapter<HorizontalRecipeAdapter.RecipeViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun setData(data: ArrayList<Recipe>) {
        asyncListDiffer.submitList(data)
    }

    private var layoutWidth = 200
    fun setLayoutWidth(width: Int) {
        layoutWidth = (width / 1.5).roundToInt()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount() = asyncListDiffer.currentList.size

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) : ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.root.setOnClickListener {
                onItemClick.invoke(recipe, binding.dishImage)
            }
            binding.root.layoutParams.width = layoutWidth
            binding.dishName.text = recipe.name
            Glide.with(binding.root.context)
                .load(recipe.image)
                .into(binding.dishImage)
        }
    }
}