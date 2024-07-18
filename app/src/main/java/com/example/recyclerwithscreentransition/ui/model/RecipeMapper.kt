package com.example.recyclerwithscreentransition.ui.model

import com.example.recyclerwithscreentransition.network.response.RecipeDetail

class RecipeMapper {
    fun convertRecipeResponseToRecipeItems(input: ArrayList<RecipeDetail>): ArrayList<RecipeItem> {
        if (input.isEmpty()) return arrayListOf()
        val result: ArrayList<RecipeItem> = ArrayList()
        input.forEach { it ->
            result.add(
                convertRecipeDetailToRecipeItems(it, RecipeItemType.RECIPE)
            )
        }

        val recommendedList: ArrayList<Recipe> = ArrayList()
        input.take(5).forEach { it ->
            recommendedList.add(convertRecipeDetailToRecipe(it))
        }
        if (recommendedList.isNotEmpty()) result.add(RecipeItem(type = RecipeItemType.RECOMMENDED, recommended = recommendedList))

        return result
    }

    private fun convertRecipeDetailToRecipeItems(input: RecipeDetail, type: RecipeItemType) =
        RecipeItem(
            type = type,
            recipe = convertRecipeDetailToRecipe(input)
        )

    private fun convertRecipeDetailToRecipe(input: RecipeDetail) =
        Recipe(
            id = input.id,
            name = input.name,
            ingredients = input.ingredients,
            instructions = input.instructions,
            prepTimeMinutes = input.prepTimeMinutes,
            cookTimeMinutes = input.cookTimeMinutes,
            servings = input.servings,
            difficulty = input.difficulty,
            cuisine = input.cuisine,
            caloriesPerServing = input.caloriesPerServing,
            tags = input.tags,
            userId = input.userId,
            image = input.image,
            rating = input.rating,
            reviewCount = input.reviewCount,
            mealType = input.mealType
        )
}