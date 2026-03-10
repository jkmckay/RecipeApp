package com.jkm.recipeapp.feature.recipe.data.api

import android.content.Context
import com.jkm.recipeapp.feature.recipe.data.RecipeResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

const val RECIPE_SAMPLE_FILENAME = "recipesSample.json"

class DummyRecipeApi
    @Inject
    constructor(
        // TODO: Sort this warning
        @param:ApplicationContext private val context: Context,
        private val json: Json,
    ) : RecipeApi {
        override suspend fun listRecipes(): RecipeResponse {
            val jsonString =
                context.assets
                    .open(RECIPE_SAMPLE_FILENAME)
                    .bufferedReader()
                    .use { it.readText() }
            // TODO: Maybe error handling if time?
            return json.decodeFromString(jsonString)
        }
    }
