package com.example.recyclerwithscreentransition.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.recyclerwithscreentransition.R
import com.example.recyclerwithscreentransition.databinding.HorizontalRecipeBinding
import com.example.recyclerwithscreentransition.databinding.ItemRecipeBinding
import com.example.recyclerwithscreentransition.ui.model.Recipe
import com.example.recyclerwithscreentransition.ui.model.RecipeItem
import com.example.recyclerwithscreentransition.ui.model.RecipeItemType
import com.example.recyclerwithscreentransition.utils.RecyclerViewDecoration

class RecipeRecyclerAdapter(
    private val onItemClick: (Recipe, View) -> Unit
): RecyclerView.Adapter<ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<RecipeItem>() {
        override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
            return oldItem.type == newItem.type && (
                    if (oldItem.type == RecipeItemType.RECIPE)
                        newItem.recipe?.id == oldItem.recipe?.id
                    else
                        newItem.recommended?.size == oldItem.recommended?.size
                    )
        }

        override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    fun setData(data: ArrayList<RecipeItem>) {
        asyncListDiffer.submitList(data)
    }

    private var layoutWidth = 200
    fun setViewWidth(width: Int) {
        layoutWidth = width
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == RECIPE_VIEW_TYPE) {
            RecipeViewHolder(ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            RecommendedRecipeViewHolder(HorizontalRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is RecipeViewHolder -> holder.bind(asyncListDiffer.currentList[position].recipe!!)
            is RecommendedRecipeViewHolder -> holder.bind(asyncListDiffer.currentList[position].recommended!!)
        }
    }

    private val RECIPE_VIEW_TYPE = 0
    private val RECOMMENDED_VIEW_TYPE = 1
    override fun getItemViewType(position: Int): Int {
        return if(asyncListDiffer.currentList[position].type == RecipeItemType.RECIPE) RECIPE_VIEW_TYPE else RECOMMENDED_VIEW_TYPE
    }

    override fun getItemCount() = asyncListDiffer.currentList.size

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) : ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.root.setOnClickListener {
                onItemClick.invoke(recipe, binding.dishImage)
            }
            binding.dishName.text = recipe.name
            Glide.with(binding.root.context)
                .load(recipe.image)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.dishImage)
        }
    }

    inner class RecommendedRecipeViewHolder(private val binding: HorizontalRecipeBinding) : ViewHolder(binding.root) {
        private val recommendedAdapter = HorizontalRecipeAdapter(onItemClick)
        fun bind(recipes: ArrayList<Recipe>) {
            binding.heading.text = "Recommended"
            binding.rvRecipe.apply {
                layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
                adapter = recommendedAdapter
                addItemDecoration(RecyclerViewDecoration(32))
            }
            recommendedAdapter.setLayoutWidth(layoutWidth)
            recommendedAdapter.setData(recipes)
        }
    }
}