package com.jkm.recipeapp.feature.recipe.data

import com.jkm.recipeapp.di.DefaultDispatcher
import com.jkm.recipeapp.di.IoDispatcher
import com.jkm.recipeapp.feature.recipe.data.api.RecipeApi
import com.jkm.recipeapp.feature.recipe.data.mappers.toDomain
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipes
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipesByTotalTime
import com.jkm.recipeapp.feature.recipe.domain.Recipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepository
    @Inject
    constructor(
        private val recipeApi: RecipeApi,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
        @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) : FlowOfRecipes,
        FlowOfRecipesByTotalTime {
        override suspend fun flowOfRecipes(): Flow<List<Recipe>> = flow { emit(recipeApi.listRecipes().toDomain()) }.flowOn(ioDispatcher)

        override suspend fun flowOfRecipesByTotalTime(): Flow<List<Recipe>> =
            flowOfRecipes()
                .map { recipes ->
                    recipes.sortedBy { recipe ->
                        recipe.details.prep.minutes + recipe.details.cookingTime.minutes
                    }
                }.flowOn(defaultDispatcher)
    }
