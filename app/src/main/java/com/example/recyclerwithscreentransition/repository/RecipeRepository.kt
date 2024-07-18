package com.example.recyclerwithscreentransition.repository

import com.example.recyclerwithscreentransition.network.APIClient
import com.example.recyclerwithscreentransition.network.response.RecipeDetail
import com.example.recyclerwithscreentransition.utils.EmptyResultException

class RecipeRepository {
    suspend fun getRecipes(): Result<ArrayList<RecipeDetail>> {
        try {
            val response = APIClient.getRetrofitClient().getRecipes()
            if (response.recipes.isEmpty()) return Result.failure(EmptyResultException())
            return Result.success(response.recipes)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}