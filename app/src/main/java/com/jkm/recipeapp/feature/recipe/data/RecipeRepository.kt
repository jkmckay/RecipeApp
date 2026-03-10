package com.jkm.recipeapp.feature.recipe.data

import com.jkm.recipeapp.feature.recipe.data.api.RecipeApi
import com.jkm.recipeapp.feature.recipe.data.mappers.toDomain
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipes
import com.jkm.recipeapp.feature.recipe.domain.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeRepository
    @Inject
    constructor(
        private val recipeApi: RecipeApi,
    ) : FlowOfRecipes {
        override suspend fun flowOfRecipes(): Flow<List<Recipe>> =
            flow {
                emit(recipeApi.listRecipes().toDomain())
            }

//        suspend fun test() {
//            val recipes = recipeApi.listRecipes().toDomain()
//            // maybe check the label == serves.
//            val thing: Map<String?, List<Recipe>> = recipeApi.listRecipes().toDomain().groupBy { it.details.amountNumber }
// //            thing.null
//
// //            val groupedRecipes: Map<String, List<Recipe>> = recipes
// //                .filter { it.details.amountLabel == "Serves" && it.details.amountNumber != null }
// //                .groupBy { it.details.amountNumber }
//
//            val groupedRecipes =
//                recipes
//                    // Ensure amount isn't described by another quantity than serves
//                    .filter { it.details.amountLabel == "Serves" }
//                    .mapNotNull { recipe -> recipe.details.amountNumber?.let { it to recipe } }
//                    .groupBy({ it.first }, { it.second })
//        }
    }
