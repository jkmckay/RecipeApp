package com.jkm.recipeapp.feature.recipe.data.api

import com.jkm.recipeapp.feature.recipe.data.RecipeResponse

interface RecipeApi {
    suspend fun listRecipes(): RecipeResponse
}
