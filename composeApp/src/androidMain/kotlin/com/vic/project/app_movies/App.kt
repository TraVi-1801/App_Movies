package com.vic.project.app_movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vic.project.app_movies.screens.detail.DetailScreen
import com.vic.project.app_movies.screens.home.HomeScreen
import com.vic.project.app_movies.utils.isOnline
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable


@Serializable
object HomeRoute

@Serializable
data class DetailRoute(val id: Int)

@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreen(navigateToDetails = { id ->
                navController.navigate(DetailRoute(id))
            })
        }
        composable<DetailRoute> { backStackEntry ->
            DetailScreen(
                id = backStackEntry.toRoute<DetailRoute>().id,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}