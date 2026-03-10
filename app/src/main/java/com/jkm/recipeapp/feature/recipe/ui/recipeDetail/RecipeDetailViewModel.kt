package com.jkm.recipeapp.feature.recipe.ui.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipes
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipes.Companion.invoke
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val flowOfRecipes: FlowOfRecipes
) : ViewModel() {

    private val _state = MutableStateFlow<RecipeDetailState>(RecipeDetailState.Loading)
    val state: StateFlow<RecipeDetailState> = _state.asStateFlow()

    private val intents = MutableSharedFlow<RecipeDetailIntent>(replay = 1)

    init {
        intents
            .flatMapLatest { intent ->
                when (intent) {
                    is RecipeDetailIntent.LoadRecipe -> {
                        flowOfRecipes()
                            .map { recipes ->
                                val recipe = recipes.find { it.title == intent.title }
                                if (recipe != null) {
                                    RecipeDetailState.Success(recipe)
                                } else {
                                    RecipeDetailState.Error("Recipe not found")
                                }
                            }
                            .onStart { emit(RecipeDetailState.Loading) }
                            .catch { e -> emit(RecipeDetailState.Error(e.message ?: "Unknown error")) }
                    }
                }
            }
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: RecipeDetailIntent) {
        intents.tryEmit(intent)
    }
}
