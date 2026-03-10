package com.jkm.recipeapp.feature.recipe.ui.recipeDetail

import com.jkm.recipeapp.feature.recipe.domain.Recipe

sealed interface RecipeDetailState {
    data object Loading : RecipeDetailState
    data class Success(val recipe: Recipe) : RecipeDetailState
    data class Error(val message: String) : RecipeDetailState
}

sealed interface RecipeDetailIntent {
    data class LoadRecipe(val title: String) : RecipeDetailIntent
}
