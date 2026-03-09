package com.jkm.recipeapp.feature.recipe.domain

// TODO: for the mappers, do  I want the toDomain or toDomainX?
// TODO: decide on what you'd consider mandatory properties versus nice to have
// TODO: Finalise domain modelling
data class Recipe(
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val thumbnailAlt: String,
    val details: RecipeDetail, // JKM - at what point do we say so much is missing that we should bin it?
    val ingredients: Ingredients,
)

// JKM - would pairs or some other modeling be better? Not showing values without their label would be good.
data class RecipeDetail(
    val amountLabel: String? = null,
    val amountNumber: String? = null,
    val prepLabel: String? = null,
    val prepNote: String? = null,
    val prepTime: String? = null,
    val cookingLabel: String? = null,
    val cookingTime: String? = null,
    val cookTimeMinutes: String? = null,
    val prepTimeMinutes: String? = null,
)
typealias Ingredients = List<Ingredient>

@JvmInline
value class Ingredient(
    val name: String,
)
