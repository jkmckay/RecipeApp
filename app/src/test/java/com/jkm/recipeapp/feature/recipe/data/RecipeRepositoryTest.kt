package com.jkm.recipeapp.feature.recipe.data

import com.jkm.recipeapp.feature.recipe.data.api.RecipeApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RecipeRepositoryTest {
    @Test
    fun `flowOfRecipes fetches and maps recipes correctly`() =
        runTest {
            // Arrange
            val recipesDto =
                RecipeResponse(
                    recipes =
                        listOf(
                            createRecipeDto("Recipe 1", 10, 20),
                            createRecipeDto("Recipe 2", 15, 25),
                        ),
                )
            val mockApi = mock<RecipeApi> { on { listRecipes() } doReturn recipesDto }
            val repository = RecipeRepository(mockApi)

            // Act
            val result = repository.flowOfRecipes().first()

            // Assert
            assertEquals(2, result.size)
            assertEquals("Recipe 1", result[0].title)
            assertEquals(10, result[0].details.prep.minutes)
            assertEquals("Recipe 2", result[1].title)
            assertEquals(25, result[1].details.cookingTime.minutes)
        }

    @Test
    fun `flowOfRecipes handles empty list`() =
        runTest {
            // Arrange
            val recipesDto = RecipeResponse(recipes = emptyList())
            val mockApi = mock<RecipeApi> { on { listRecipes() } doReturn recipesDto }
            val repository = RecipeRepository(mockApi)

            // Act
            val result = repository.flowOfRecipes().first()

            // Assert
            assertTrue(result.isEmpty())
        }

    @Test
    fun `flowOfRecipes filters out malformed data`() =
        runTest {
            // Arrange
            val recipesDto =
                RecipeResponse(
                    recipes =
                        listOf(
                            createRecipeDto("Good Recipe", 10, 20),
                            createRecipeDto(null, 15, 25), // Malformed entry
                        ),
                )
            val mockApi = mock<RecipeApi> { on { listRecipes() } doReturn recipesDto }
            val repository = RecipeRepository(mockApi)

            // Act
            val result = repository.flowOfRecipes().first()

            // Assert
            assertEquals(1, result.size)
            assertEquals("Good Recipe", result.first().title)
        }

    @Test
    fun `flowOfRecipesByTotalTime sorts recipes by sum of prep and cooking minutes`() =
        runTest {
            // Arrange
            val recipesDto =
                RecipeResponse(
                    recipes =
                        listOf(
                            createRecipeDto("Slow", 30, 60), // 90 mins
                            createRecipeDto("Fast", 5, 10), // 15 mins
                            createRecipeDto("Medium", 15, 20), // 35 mins
                        ),
                )
            val mockApi = mock<RecipeApi> { on { listRecipes() } doReturn recipesDto }
            val repository = RecipeRepository(mockApi)

            // Act
            val result = repository.flowOfRecipesByTotalTime().first()

            // Assert
            assertEquals(3, result.size)
            assertEquals("Fast", result[0].title)
            assertEquals("Medium", result[1].title)
            assertEquals("Slow", result[2].title)
        }

    private fun createRecipeDto(
        title: String?,
        prepMins: Int,
        cookMins: Int,
    ): RecipeDto =
        RecipeDto(
            title = title,
            description = "Desc",
            thumbnailRelativePath = "/path.jpg",
            thumbnailAlt = "Alt",
            details =
                RecipeDetailsDto(
                    amountLabel = "Serves",
                    amountNumber = 4,
                    prepLabel = "Prep",
                    prepNote = "",
                    prepTime = "${prepMins}m",
                    prepTimeMinutes = prepMins,
                    cookingLabel = "Cooking",
                    cookingTime = "${cookMins}m",
                    cookTimeMinutes = cookMins,
                ),
            ingredients = listOf(IngredientDto("Item")),
        )
}
