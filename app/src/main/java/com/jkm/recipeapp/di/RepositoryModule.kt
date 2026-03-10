package com.jkm.recipeapp.di

import com.jkm.recipeapp.feature.recipe.data.RecipeRepository
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipes
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipesByTotalTime
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFlowOfRecipes(recipeRepository: RecipeRepository): FlowOfRecipes

    @Binds
    @Singleton
    abstract fun bindFlowOfRecipesByTotalTime(recipeRepository: RecipeRepository): FlowOfRecipesByTotalTime
}
