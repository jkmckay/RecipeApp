package com.jkm.recipeapp.feature.recipe.ui.recipeList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jkm.recipeapp.R
import com.jkm.recipeapp.feature.recipe.domain.Recipe

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    windowWidthSizeClass: WindowWidthSizeClass,
    viewModel: RecipeListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RecipeListScreen(
        modifier = modifier,
        state = state,
        onIntent = viewModel::onIntent,
        windowWidthSizeClass = windowWidthSizeClass,
    )
}

@Composable
fun RecipeListScreen(
    state: RecipeListState,
    onIntent: (RecipeListIntent) -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    RecipeListContent(
        state = state,
        onIntent = onIntent,
        windowWidthSizeClass = windowWidthSizeClass,
        modifier = modifier,
    )
}

@Composable
private fun RecipeListContent(
    state: RecipeListState,
    onIntent: (RecipeListIntent) -> Unit,
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
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            if (recipes.isNotEmpty()) {
                RecipeDetail(recipes.first())
            }
        }

        else -> {
            RecipeGrid(
                recipes = recipes,
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
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(recipe.thumbnail.url)
                    .crossfade(true)
                    .build(),
            placeholder = painterResource(R.drawable.loading_placeholder),
            error = painterResource(R.drawable.loading_placeholder),
            contentDescription = recipe.thumbnail.altText,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            RecipeSummaryItem(
                label = recipe.details.amount.label,
                value =
                    recipe.details.amount.value
                        .toString(),
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(modifier = Modifier.fillMaxHeight())
            RecipeSummaryItem(
                label = recipe.details.prep.label,
                value = recipe.details.prep.time,
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(modifier = Modifier.fillMaxHeight())
            RecipeSummaryItem(
                label = recipe.details.cookingTime.label,
                value = recipe.details.cookingTime.time,
                modifier = Modifier.weight(1f),
            )
        }

        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
        ) {
            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            recipe.ingredients.forEach { ingredient ->
                Row(
                    modifier = Modifier.padding(vertical = 8.dp).semantics(mergeDescendants = true) {},
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = "›",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(end = 8.dp),
                    )
                    Text(
                        text = ingredient.name,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Composable
private fun RecipeSummaryItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.semantics(mergeDescendants = true) {},
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
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
            )
        }
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
) {
    Card(
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
                placeholder = painterResource(R.drawable.loading_placeholder),
                error = painterResource(R.drawable.loading_placeholder),
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
