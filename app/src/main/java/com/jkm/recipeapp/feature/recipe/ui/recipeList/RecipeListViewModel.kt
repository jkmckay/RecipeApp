package com.jkm.recipeapp.feature.recipe.ui.recipeList

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipes
import com.jkm.recipeapp.feature.recipe.domain.FlowOfRecipes.Companion.invoke
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val flowOfRecipes: FlowOfRecipes,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<RecipeListState> = refreshTrigger
        .flatMapLatest {
            flow {
                emit(RecipeListState.Loading)
                emitAll(flowOfRecipes().map { RecipeListState.Success(it) })
            }
        }
        .onEach { Log.d("JKM", "State update: $it") }
        .catch { e ->
            Log.e("JKM", "Error fetching recipes", e)
            emit(RecipeListState.Error(e.message ?: "Unknown error"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RecipeListState.Loading
        )

    fun refresh() {
        refreshTrigger.tryEmit(Unit)
    }
}
