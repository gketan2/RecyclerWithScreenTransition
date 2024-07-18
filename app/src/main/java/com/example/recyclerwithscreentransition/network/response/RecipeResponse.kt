package com.example.recyclerwithscreentransition.network.response

import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("recipes") var recipes : ArrayList<RecipeDetail> = arrayListOf(),
    @SerializedName("total"  ) var total   : Int?              = null,
    @SerializedName("skip"   ) var skip    : Int?              = null,
    @SerializedName("limit"  ) var limit   : Int?              = null
)

data class RecipeDetail (
    @SerializedName("id"                 ) var id                 : Int?              = null,
    @SerializedName("name"               ) var name               : String?           = null,
    @SerializedName("ingredients"        ) var ingredients        : ArrayList<String> = arrayListOf(),
    @SerializedName("instructions"       ) var instructions       : ArrayList<String> = arrayListOf(),
    @SerializedName("prepTimeMinutes"    ) var prepTimeMinutes    : Int?              = null,
    @SerializedName("cookTimeMinutes"    ) var cookTimeMinutes    : Int?              = null,
    @SerializedName("servings"           ) var servings           : Int?              = null,
    @SerializedName("difficulty"         ) var difficulty         : String?           = null,
    @SerializedName("cuisine"            ) var cuisine            : String?           = null,
    @SerializedName("caloriesPerServing" ) var caloriesPerServing : Int?              = null,
    @SerializedName("tags"               ) var tags               : ArrayList<String> = arrayListOf(),
    @SerializedName("userId"             ) var userId             : Int?              = null,
    @SerializedName("image"              ) var image              : String?           = null,
    @SerializedName("rating"             ) var rating             : Double?           = null,
    @SerializedName("reviewCount"        ) var reviewCount        : Int?              = null,
    @SerializedName("mealType"           ) var mealType           : ArrayList<String> = arrayListOf()

)