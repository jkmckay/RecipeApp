package com.jkm.recipeapp.di

import com.jkm.recipeapp.feature.recipe.data.api.DummyRecipeApi
import com.jkm.recipeapp.feature.recipe.data.api.RecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

    @Provides
    @Singleton
    fun provideRecipeApi(dummyRecipeApi: DummyRecipeApi): RecipeApi = dummyRecipeApi
}
