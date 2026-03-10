package com.jkm.recipeapp.feature.recipe.ui.recipeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipesByTotalTime
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipesByTotalTime.Companion.invoke
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
class RecipeListViewModel
    @Inject
    constructor(
        private val flowOfRecipesByTotalTime: FlowOfRecipesByTotalTime,
    ) : ViewModel() {
        private val _state = MutableStateFlow<RecipeListState>(RecipeListState.Loading)
        val state: StateFlow<RecipeListState> = _state.asStateFlow()

        private val intents =
            MutableSharedFlow<RecipeListIntent>(replay = 1).apply {
                tryEmit(RecipeListIntent.Refresh)
            }

        init {
            intents
                .flatMapLatest { intent ->
                    when (intent) {
                        is RecipeListIntent.Refresh -> {
                            flowOfRecipesByTotalTime()
                                .map { recipes -> RecipeListState.Success(recipes) }
                                .onStart<RecipeListState> { emit(RecipeListState.Loading) }
                                .catch { e -> emit(RecipeListState.Error(e.message ?: "Unknown error")) }
                        }
                    }
                }.onEach { _state.value = it }
                .launchIn(viewModelScope)
        }

        fun onIntent(intent: RecipeListIntent) {
            intents.tryEmit(intent)
        }
    }
