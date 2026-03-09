package com.jkm.recipeapp.feature.recipe.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Do you need to be a class or is a typealias fine?
// TODO: value class maybe?
@Serializable
data class RecipeResponse(
    @SerialName("recipes") val recipes: List<RecipeDto>? = null,
)

@Serializable
data class RecipeDto(
    @SerialName("dynamicTitle") val title: String? = null,
    @SerialName("dynamicDescription") val description: String? = null,
    @SerialName("dynamicThumbnail") val thumbnailUrl: String? = null,
    @SerialName("dynamicThumbnailAlt") val thumbnailAlt: String? = null,
    @SerialName("recipeDetails") val details: RecipeDetailsDto? = null,
    @SerialName("ingredients") val ingredients: List<IngredientDto>? = null,
)

// TODO: Confirm this - suss about keeping the Ints as ints, String may be better 
@Serializable
data class RecipeDetailsDto(
    @SerialName("amountLabel") val amountLabel: String? = null,
    @SerialName("amountNumber") val amountNumber: Int? = null,
//    @SerialName("amountNumber") val amountNumber: String? = null,
    @SerialName("prepLabel") val prepLabel: String? = null,
    @SerialName("prepNote") val prepNote: String? = null,
    @SerialName("prepTime") val prepTime: String? = null,
    @SerialName("cookingLabel") val cookingLabel: String? = null,
    @SerialName("cookingTime") val cookingTime: String? = null,
    @SerialName("cookTimeAsMinutes") val cookTimeMinutes: Int? = null,
//    @SerialName("cookTimeAsMinutes") val cookTimeMinutes: String? = null,
    @SerialName("prepTimeAsMinutes") val prepTimeMinutes: Int? = null,
//    @SerialName("prepTimeAsMinutes") val prepTimeMinutes: String? = null,
)

@Serializable
data class IngredientDto(
    @SerialName("ingredient") val name: String? = null,
)
