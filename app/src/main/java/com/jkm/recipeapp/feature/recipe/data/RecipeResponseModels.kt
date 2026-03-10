package com.jkm.recipeapp.feature.recipe.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName("recipes") val recipes: List<RecipeDto?>? = null,
)

@Serializable
data class RecipeDto(
    @SerialName("dynamicTitle") val title: String? = null,
    @SerialName("dynamicDescription") val description: String? = null,
    @SerialName("dynamicThumbnail") val thumbnailRelativePath: String? = null,
    @SerialName("dynamicThumbnailAlt") val thumbnailAlt: String? = null,
    @SerialName("recipeDetails") val details: RecipeDetailsDto? = null,
    @SerialName("ingredients") val ingredients: List<IngredientDto?>? = null,
)

@Serializable
data class RecipeDetailsDto(
    @SerialName("amountLabel") val amountLabel: String? = null,
    @SerialName("amountNumber") val amountNumber: Int? = null,
    @SerialName("prepLabel") val prepLabel: String? = null,
    @SerialName("prepNote") val prepNote: String? = null,
    @SerialName("prepTime") val prepTime: String? = null,
    @SerialName("cookingLabel") val cookingLabel: String? = null,
    @SerialName("cookingTime") val cookingTime: String? = null,
    @SerialName("cookTimeAsMinutes") val cookTimeMinutes: Int? = null,
    @SerialName("prepTimeAsMinutes") val prepTimeMinutes: Int? = null,
)

@Serializable
data class IngredientDto(
    @SerialName("ingredient") val value: String? = null,
)
