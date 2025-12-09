package com.example.cvsapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cvsapplication.ui.screens.CharacterDetailScreen
import com.example.cvsapplication.ui.screens.CharacterListScreen
import com.example.cvsapplication.ui.viewmodel.CharacterDetailViewModel
import com.example.cvsapplication.ui.viewmodel.CharacterListViewModel

/**
 * author lgarg on 12/9/25.
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CharacterList.route,
        modifier = modifier
    ) {
        // LIST → start screen
        composable(Screen.CharacterList.route) { backStackEntry ->
            val listViewModel: CharacterListViewModel = hiltViewModel(backStackEntry)

            CharacterListScreen(
                viewModel = listViewModel,
                onCharacterClick = { id ->
                    navController.navigate(Screen.CharacterDetail.routeWithId(id))
                },
                onFilterClick = { /* TODO later */ }
            )
        }

        // DETAIL
        composable(
            route = Screen.CharacterDetail.route,
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val detailViewModel: CharacterDetailViewModel = hiltViewModel(backStackEntry)
            val characterId = backStackEntry.arguments?.getInt("id") ?: return@composable

            CharacterDetailScreen(
                viewModel = detailViewModel,
                characterId = characterId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}