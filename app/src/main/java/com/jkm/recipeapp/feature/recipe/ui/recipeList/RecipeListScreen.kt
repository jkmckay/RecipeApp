package com.jkm.recipeapp.feature.recipe.ui.recipeList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jkm.recipeapp.feature.recipe.domain.Recipe

@Composable
fun RecipeListScreen(
    onRecipeClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
    windowWidthSizeClass: WindowWidthSizeClass,
    viewModel: RecipeListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RecipeListScreen(
        modifier = modifier,
        state = state,
        onIntent = viewModel::onIntent,
        onRecipeClick = onRecipeClick,
        windowWidthSizeClass = windowWidthSizeClass,
    )
}

@Composable
fun RecipeListScreen(
    state: RecipeListState,
    onIntent: (RecipeListIntent) -> Unit,
    onRecipeClick: (Recipe) -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    RecipeListContent(
        state = state,
        onIntent = onIntent,
        onRecipeClick = onRecipeClick,
        windowWidthSizeClass = windowWidthSizeClass,
        modifier = modifier,
    )
}

@Composable
private fun RecipeListContent(
    state: RecipeListState,
    onIntent: (RecipeListIntent) -> Unit,
    onRecipeClick: (Recipe) -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is RecipeListState.Loading -> {
                CircularProgressIndicator(
                    modifier =
                        Modifier.align(
                            Alignment.Center,
                        ),
                )
            }

            is RecipeListState.Success -> {
                RecipeListContent(
                    recipes = state.recipes,
                    onRecipeClick = onRecipeClick,
                    windowWidthSizeClass = windowWidthSizeClass,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            is RecipeListState.Error -> {
                ErrorScreen(
                    message = state.message,
                    onRetry = { onIntent(RecipeListIntent.Refresh) },
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
private fun RecipeListContent(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            RecipeDetail(recipes.first())
        }

        else -> {
            RecipeGrid(
                recipes = recipes,
                onRecipeClick = onRecipeClick,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun RecipeDetail(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(recipe.thumbnail.url)
                    .crossfade(true)
                    .build(),
            contentDescription = recipe.thumbnail.url,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop,
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyLarge,
            )

            // Add more details like ingredients, instructions if available in domain model
            if (recipe.ingredients.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleLarge,
                )
                recipe.ingredients.forEach { ingredient ->
                    Text(
                        text = "• ${ingredient.name}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
        )
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun RecipeGrid(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier,
    ) {
        items(recipes) { recipe ->
            RecipeCard(
                recipe = recipe,
                onClick = { onRecipeClick(recipe) },
            )
        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column {
            AsyncImage(
                model =
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(recipe.thumbnail.url)
                        .crossfade(true)
                        .build(),
                contentDescription = recipe.thumbnail.altText,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
