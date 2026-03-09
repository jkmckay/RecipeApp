package com.jkm.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.jkm.recipeapp.feature.recipe.ui.recipeList.RecipeListScreen
import com.jkm.recipeapp.ui.theme.RecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding),
//                    )
                    RecipeListScreen()
                }
            }
        }
    }
}
// TODO: Decide on Enro or Compose nav

//@Composable
//fun Greeting(
//    name: String,
//    modifier: Modifier = Modifier,
//) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier,
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    RecipeAppTheme {
//        Greeting("Android")
//    }
//}
