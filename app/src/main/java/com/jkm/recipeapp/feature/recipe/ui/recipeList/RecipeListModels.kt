package com.jkm.recipeapp.feature.recipe.ui.recipeList

import com.jkm.recipeapp.feature.recipe.domain.Recipe

// Normally there'd be a generic version of this for reuse.
sealed interface RecipeListState {
    data object Loading : RecipeListState
    data class Success(val recipes: List<Recipe>) : RecipeListState
    data class Error(val message: String) : RecipeListState
}

