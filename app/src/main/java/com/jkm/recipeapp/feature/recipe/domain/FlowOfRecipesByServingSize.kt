package com.jkm.recipeapp.feature.recipe.domain

import kotlinx.coroutines.flow.Flow

fun interface FlowOfRecipesByServingSize {
    suspend fun flowOfRecipesByServingSize(): Flow<List<Recipe>>

    companion object {
        suspend operator fun FlowOfRecipesByServingSize.invoke() = flowOfRecipesByServingSize()
    }
}
