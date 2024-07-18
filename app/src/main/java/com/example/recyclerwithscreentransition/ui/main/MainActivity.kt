package com.example.recyclerwithscreentransition.ui.main

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerwithscreentransition.databinding.ActivityMainBinding
import com.example.recyclerwithscreentransition.model.ResultWrapper
import com.example.recyclerwithscreentransition.ui.detail.RecipeDetailActivity
import com.example.recyclerwithscreentransition.ui.model.RecipeItem
import com.example.recyclerwithscreentransition.utils.EmptyResultException
import com.example.recyclerwithscreentransition.utils.RecyclerViewDecoration
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val recipeAdapter: RecipeRecyclerAdapter by lazy {
        RecipeRecyclerAdapter { recipe, view ->
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("recipe", recipe)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, ViewCompat.getTransitionName(view)!!)
            startActivity(intent, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvRecipe.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = recipeAdapter
            addItemDecoration(RecyclerViewDecoration(64))
            setHasFixedSize(true)
        }
        binding.rvRecipe.post {
            recipeAdapter.setViewWidth(binding.rvRecipe.width)
        }
        viewModel.recipeLiveData.observe(this) { response ->
            when (response) {
                is ResultWrapper.Failed -> {
                    showFailedUI(response.msg, response.e)
                }
                is ResultWrapper.Loading -> {
                    showLoading(true)
                }
                is ResultWrapper.Success -> {
                    showData(response.data)
                }
            }
        }
        val fade = Fade()
        //val decor = window.decorView
        //fade.excludeTarget(decor.findViewById(androidx.appcompat.R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        window.enterTransition = fade
        window.exitTransition = fade
    }

    private fun showData(data: ArrayList<RecipeItem>) {
        showLoading(false)
        binding.rvRecipe.visibility = View.VISIBLE
        recipeAdapter.setData(data)
    }

    private fun showFailedUI(msg: String?, throwable: Throwable?) {
        showLoading(false)
        if (throwable is EmptyResultException) {
            //show no recipes
        } else {
            //other error
            msg?.let {Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show() }
        }
    }
    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.rvRecipe.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmer()
        } else {
            binding.shimmerLayout.visibility = View.GONE
            binding.shimmerLayout.stopShimmer()
        }
    }
}