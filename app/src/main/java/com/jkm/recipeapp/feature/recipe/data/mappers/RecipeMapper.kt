package com.jkm.recipeapp.feature.recipe.data.mappers

import com.jkm.recipeapp.feature.recipe.data.RecipeDetailsDto
import com.jkm.recipeapp.feature.recipe.data.RecipeDto
import com.jkm.recipeapp.feature.recipe.data.RecipeResponse
import com.jkm.recipeapp.feature.recipe.domain.Amount
import com.jkm.recipeapp.feature.recipe.domain.CookingTime
import com.jkm.recipeapp.feature.recipe.domain.Ingredient
import com.jkm.recipeapp.feature.recipe.domain.PrepTime
import com.jkm.recipeapp.feature.recipe.domain.Recipe
import com.jkm.recipeapp.feature.recipe.domain.RecipeDetails
import com.jkm.recipeapp.feature.recipe.domain.Thumbnail
import kotlin.collections.mapNotNull

const val BASE_URL = "https://www.coles.com.au"

val altTextFallback = "Recipe Image"

// Treating missing properties as 'malformed' and dropping them,except the prepNote which appears
// to be optional from the sample json
fun RecipeResponse.toDomain(): List<Recipe> = recipes.toDomain()

fun List<RecipeDto?>?.toDomain(): List<Recipe> = this?.mapNotNull { it?.toDomain() } ?: emptyList()

fun RecipeDto.toDomain(): Recipe? =
    Recipe(
        title = title ?: return null,
        description = description ?: return null,
        thumbnail =
            Thumbnail(
                url = thumbnailRelativePath?.takeIf { it.isNotBlank() }?.toAbsoluteUrl() ?: return null,
                altText = thumbnailAlt ?: altTextFallback,
            ),
        details = details?.toDomain() ?: return null,
        // rather than filtering out null ingredients, dropping entire recipe if missing
        // ingredients/empty.
        ingredients =
            ingredients
                ?.mapNotNull { it?.value?.let(::Ingredient) }
                ?.takeIf { it.size == ingredients.size && it.isNotEmpty() }
                ?: return null,
    )

fun RecipeDetailsDto.toDomain(): RecipeDetails? =
    RecipeDetails(
        amount =
            Amount(
                label = amountLabel ?: return null,
                value = amountNumber ?: return null,
            ),
        prep =
            PrepTime(
                label = prepLabel ?: return null,
                note = prepNote,
                time = prepTime ?: return null,
                minutes = prepTimeMinutes ?: return null,
            ),
        cookingTime =
            CookingTime(
                label = cookingLabel ?: return null,
                time = cookingTime ?: return null,
                minutes = cookTimeMinutes ?: return null,
            ),
    )

// Would normally have a proper image loader/url constructor but keeping it simple.
fun String.toAbsoluteUrl() = BASE_URL + this
