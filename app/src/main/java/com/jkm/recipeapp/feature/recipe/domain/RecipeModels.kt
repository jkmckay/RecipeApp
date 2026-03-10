package com.jkm.recipeapp.feature.recipe.domain

data class Recipe(
    val title: String,
    val description: String,
    val thumbnail: Thumbnail,
    val details: RecipeDetails,
    val ingredients: List<Ingredient>,
)

data class RecipeDetails(
    val amount: Amount,
    val prep: PrepTime,
    val cookingTime: CookingTime,
)

data class Thumbnail(
    val url: String,
    val altText: String,
)

data class Amount(
    val label: String,
    val value: Int,
)

data class PrepTime(
    val label: String,
    val note: String?,
    val time: String,
    val minutes: Int,
)

data class CookingTime(
    val label: String,
    val time: String,
    val minutes: Int,
)

@JvmInline
value class Ingredient(
    val name: String,
)
