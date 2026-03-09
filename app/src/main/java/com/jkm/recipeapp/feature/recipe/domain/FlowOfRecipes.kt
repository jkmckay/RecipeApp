package com.jkm.recipeapp.feature.recipe.domain

import kotlinx.coroutines.flow.Flow

// TODO: should this be suspend
fun interface FlowOfRecipes {
     suspend fun flowOfRecipes(): Flow<List<Recipe>>

    companion object {
         suspend operator fun FlowOfRecipes.invoke() = flowOfRecipes()
    }
}
