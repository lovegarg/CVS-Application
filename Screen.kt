package com.example.cvsapplication.ui.navigation

/**
 * author lgarg on 12/9/25.
 */
sealed class Screen(val route: String) {

    data object CharacterList : Screen("character_list")

    data object CharacterDetail : Screen("character_detail/{id}") {
        fun routeWithId(id: Int) = "character_detail/$id"
    }
}