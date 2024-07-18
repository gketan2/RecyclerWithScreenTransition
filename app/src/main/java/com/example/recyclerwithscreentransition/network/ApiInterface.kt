package com.example.recyclerwithscreentransition.network

import com.example.recyclerwithscreentransition.network.response.RecipeResponse
import retrofit2.http.GET

interface ApiInterface {
    @GET("recipes")
    suspend fun getRecipes(): RecipeResponse
}