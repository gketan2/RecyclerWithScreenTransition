package com.example.recyclerwithscreentransition.ui.detail

import android.R
import android.os.Build
import android.os.Bundle
import android.transition.Fade
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.recyclerwithscreentransition.databinding.ActivityRecipeDetailBinding
import com.example.recyclerwithscreentransition.ui.model.Recipe
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch


class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val data: Recipe? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra("recipe", Recipe::class.java)
        else
            intent.getSerializableExtra("recipe") as? Recipe

        if (data == null) {
            finish()
        }
        recipe = data!!

        val fade = Fade()
        //val decor = window.decorView
        //fade.excludeTarget(decor.findViewById<View>(R.id.action_bar_container), true)
        fade.excludeTarget(R.id.statusBarBackground, true)
        fade.excludeTarget(R.id.navigationBarBackground, true)
        window.enterTransition = fade
        window.exitTransition = fade

        Glide.with(baseContext).load(recipe.image).into(binding.dishImage)

        lifecycleScope.launch {
            setData()
        }
    }
    private fun setData() {
        binding.dishName.text = recipe.name
        binding.calorie.text = "${recipe.caloriesPerServing}kcal"
        binding.time.text = "${recipe.cookTimeMinutes?:0 + (recipe.prepTimeMinutes?:0)}min"
        binding.rating.text = "${recipe.rating}"


        if (recipe.tags.isEmpty()) {
            binding.tagsGroup.visibility = View.GONE
        } else {
            recipe.tags.forEach { tag ->
                binding.tagsGroup.addView(
                    Chip(this).apply {
                        text = tag
                        isClickable = false
                    }
                )
            }
        }

        val ingredients = StringBuffer()
        recipe.ingredients.forEachIndexed { index, s ->
            ingredients.append(s)
            if (index != recipe.ingredients.size-1)
                ingredients.append("\n")
        }
        if (ingredients.isEmpty()) {
            binding.ingredients.visibility = View.GONE
            binding.ingredientsHeading.visibility = View.GONE
        } else {
            binding.ingredients.text = ingredients.toString()
        }

        val instruction = StringBuffer()
        recipe.instructions.forEachIndexed { index, s ->
            instruction.append(s)
            if (index != recipe.instructions.size-1)
                instruction.append("\n")
        }
        if (instruction.isEmpty()) {
            //hide
            binding.instruction.visibility = View.GONE
            binding.instructionHeading.visibility = View.GONE
        } else {
            binding.instruction.text = instruction.toString()
        }
    }
}