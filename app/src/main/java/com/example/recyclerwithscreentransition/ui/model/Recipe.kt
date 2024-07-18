package com.example.recyclerwithscreentransition.ui.model

import java.io.Serializable

enum class RecipeItemType {
    RECIPE,
    RECOMMENDED
}

data class RecipeItem(
    val type: RecipeItemType,
    val recipe: Recipe? = null,
    val recommended: ArrayList<Recipe>? = null
)

data class Recipe(
    val id: Int? = null,
    val name: String? = null,
    val ingredients: java.util.ArrayList<String> = arrayListOf(),
    val instructions: java.util.ArrayList<String> = arrayListOf(),
    val prepTimeMinutes: Int? = null,
    val cookTimeMinutes: Int? = null,
    val servings: Int? = null,
    val difficulty: String? = null,
    val cuisine: String? = null,
    val caloriesPerServing: Int? = null,
    val tags: java.util.ArrayList<String> = arrayListOf(),
    val userId: Int? = null,
    val image: String? = null,
    val rating: Double? = null,
    val reviewCount: Int? = null,
    val mealType: java.util.ArrayList<String> = arrayListOf()
): Serializable
