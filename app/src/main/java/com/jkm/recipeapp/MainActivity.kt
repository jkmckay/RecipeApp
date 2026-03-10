package com.jkm.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.jkm.recipeapp.feature.recipe.ui.recipeDetail.RecipeDetailScreen
import com.jkm.recipeapp.feature.recipe.ui.recipeList.RecipeListScreen
import com.jkm.recipeapp.navigation.Navigator
import com.jkm.recipeapp.navigation.RecipeDetail
import com.jkm.recipeapp.navigation.RecipeList
import com.jkm.recipeapp.navigation.rememberNavigationState
import com.jkm.recipeapp.navigation.toEntries
import com.jkm.recipeapp.ui.theme.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                val navigationState = rememberNavigationState(
                    startRoute = RecipeList,
                    topLevelRoutes = setOf(RecipeList)
                )
                val navigator = remember { Navigator(navigationState) }

                val entryProvider = entryProvider {
                    entry<RecipeList> {
                        RecipeListScreen(
                            onRecipeClick = { recipe ->
                                navigator.navigate(RecipeDetail(recipeTitle = recipe.title))
                            }
                        )
                    }
                    entry<RecipeDetail> { key ->
                        RecipeDetailScreen(
                            recipeTitle = key.recipeTitle,
                            onBackClick = { navigator.goBack() }
                        )
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavDisplay(
                        entries = navigationState.toEntries(entryProvider),
                        onBack = { navigator.goBack() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
