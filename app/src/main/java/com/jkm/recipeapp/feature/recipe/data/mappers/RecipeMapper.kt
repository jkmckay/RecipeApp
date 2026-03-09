package com.jkm.recipeapp.feature.recipe.data.mappers

import com.jkm.recipeapp.feature.recipe.data.IngredientDto
import com.jkm.recipeapp.feature.recipe.data.RecipeDetailsDto
import com.jkm.recipeapp.feature.recipe.data.RecipeResponse
import com.jkm.recipeapp.feature.recipe.domain.Ingredient
import com.jkm.recipeapp.feature.recipe.domain.Recipe
import com.jkm.recipeapp.feature.recipe.domain.RecipeDetail

const val BASE_URL = "https://www.coles.com.au"

fun RecipeResponse.toDomain(): List<Recipe> =
    recipes?.mapNotNull {
        Recipe(
            title = it.title ?: return@mapNotNull null,
            description = it.description ?: return@mapNotNull null,
            thumbnailUrl = it.thumbnailUrl?.toRemoteImageUrl() ?: return@mapNotNull null,
            thumbnailAlt = it.thumbnailAlt ?: return@mapNotNull null,
            details = it.details?.toDomain() ?: return@mapNotNull null,
            ingredients = it.ingredients?.toDomain()?.filterNotNull() ?: return@mapNotNull null,
        )
    }?:emptyList()

fun RecipeDetailsDto.toDomain() = RecipeDetail(
    amountLabel = amountLabel,
    amountNumber = amountNumber.toString(),
    prepLabel = prepLabel,
    prepNote = prepNote,
    prepTime = prepTime
)

fun List<IngredientDto>.toDomain(): List<Ingredient?> = map {
    Ingredient(
        name = it.name ?: return@map null
    )
}
// Would normally have a proper image loader/url constructor but keeping it simple.
fun String?.toRemoteImageUrl() = BASE_URL + this
