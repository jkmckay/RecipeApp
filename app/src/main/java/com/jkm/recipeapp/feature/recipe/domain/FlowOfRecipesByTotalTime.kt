package com.jkm.recipeapp.feature.recipe.domain

import kotlinx.coroutines.flow.Flow

fun interface FlowOfRecipesByTotalTime {
    suspend fun flowOfRecipesByTotalTime(): Flow<List<Recipe>>

    companion object {
        suspend operator fun FlowOfRecipesByTotalTime.invoke() = flowOfRecipesByTotalTime()
    }
}
