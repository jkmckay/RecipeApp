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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jkm.recipeapp.feature.recipe.domain.Recipe

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RecipeListScreen(
        modifier = modifier,
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun RecipeListScreen(
    state: RecipeListState,
    onIntent: (RecipeListIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    RecipeListContent(
        state = state,
        onIntent = onIntent,
        modifier = modifier
    )
}

@Composable
private fun RecipeListContent(
    state: RecipeListState,
    onIntent: (RecipeListIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is RecipeListState.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )

            is RecipeListState.Success -> RecipeListContent(
                recipes = state.recipes,
                modifier = Modifier.fillMaxSize()
            )

            is RecipeListState.Error -> {
                ErrorScreen(
                    message = state.message,
                    onRetry = { onIntent(RecipeListIntent.Refresh) },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}


@Composable
private fun RecipeListContent(
    recipes: List<Recipe>,
    modifier: Modifier = Modifier
) {
    RecipeGrid(
        recipes = recipes,
        modifier = modifier
    )
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun RecipeGrid(
    recipes: List<Recipe>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe)
        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            AsyncImage(
                model =
                    ImageRequest.Builder(LocalContext.current)
                        .data(recipe.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                contentDescription = recipe.thumbnailAlt,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
