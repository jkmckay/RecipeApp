package com.jkm.recipeapp.feature.recipe.ui.recipeList

import android.util.Log
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
    private val flowOfRecipes: FlowOfRecipes
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
            }.catch { e ->
                emit(RecipeListState.Error(e.message ?: "Unknown error"))
            }
        }
        .onEach { Log.d("JKM", "State update: $it") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = RecipeListState.Loading
        )

    fun refresh() {
        refreshTrigger.tryEmit(Unit)
    }
}
