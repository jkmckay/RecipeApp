package com.jkm.recipeapp.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object RecipeList : NavKey

@Serializable
data class RecipeDetail(
    val recipeTitle: String,
) : NavKey
