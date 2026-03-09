package com.jkm.recipeapp.di

import com.jkm.recipeapp.feature.recipe.data.api.DummyRecipeApi
import com.jkm.recipeapp.feature.recipe.data.api.RecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://dummy.api/") // Base URL is required but ignored if you use @Url
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    //    fun provideRecipeApi(retrofit: Retrofit): RecipeApi {
//        return retrofit.create(RecipeApi::class.java)
//    }
    @Provides
    @Singleton
    fun provideRecipeApi(dummyRecipeApi: DummyRecipeApi): RecipeApi {
        return dummyRecipeApi
    }
}