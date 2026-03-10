package com.jkm.recipeapp.feature.recipe.domain

import kotlinx.coroutines.flow.Flow

fun interface FlowOfRecipes {
    suspend fun flowOfRecipes(): Flow<List<Recipe>>

    companion object {
        suspend operator fun FlowOfRecipes.invoke() = flowOfRecipes()
    }
}
