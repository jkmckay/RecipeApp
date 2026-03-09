package com.jkm.recipeapp.feature.recipe.data

import com.jkm.recipeapp.feature.recipe.data.api.RecipeApi
import com.jkm.recipeapp.feature.recipe.data.mappers.toDomain
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipes
import com.jkm.recipeapp.feature.recipe.domain.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeApi: RecipeApi
): FlowOfRecipes {
    override suspend fun flowOfRecipes(): Flow<List<Recipe>> = flow {
        emit(recipeApi.listRecipes().toDomain())
    }
}
