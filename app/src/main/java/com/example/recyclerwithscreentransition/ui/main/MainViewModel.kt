package com.example.recyclerwithscreentransition.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerwithscreentransition.model.ResultWrapper
import com.example.recyclerwithscreentransition.repository.RecipeRepository
import com.example.recyclerwithscreentransition.ui.model.RecipeItem
import com.example.recyclerwithscreentransition.ui.model.RecipeMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val recipeRepository = RecipeRepository()
    private val recipeMapper = RecipeMapper()

    private val _recipeLiveData: MutableLiveData<ResultWrapper<ArrayList<RecipeItem>>> = MutableLiveData()
    val recipeLiveData: LiveData<ResultWrapper<ArrayList<RecipeItem>>> = _recipeLiveData


    init {
        fetchRecipes()
    }

    fun fetchRecipes() = viewModelScope.launch(Dispatchers.IO) {
        if (_recipeLiveData.value is ResultWrapper.Loading) return@launch

        _recipeLiveData.postValue(ResultWrapper.Loading())
        val resp = recipeRepository.getRecipes()
        if (resp.isSuccess) {
            _recipeLiveData.postValue(
                ResultWrapper.Success(
                    recipeMapper.convertRecipeResponseToRecipeItems(
                        resp.getOrDefault(ArrayList())
                    )
                )
            )
        } else {
            _recipeLiveData.postValue(
                ResultWrapper.Failed(
                    "Something Went Wrong..",
                    resp.exceptionOrNull()
                )
            )
        }
    }
}