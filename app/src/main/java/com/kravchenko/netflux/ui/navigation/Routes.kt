package com.kravchenko.netflux.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibleForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccessibleForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
sealed class Routes(
    val route: String,
    val name: String? = route,
    val icon: ImageVector? = null,
    val selectedIcon: ImageVector? = null,
    val iconDescription: String? = ""
) {
    data object Auth : Routes("Auth")
    data object Main : Routes("Main")

    data object Landing : Routes("Landing")
    data object Login : Routes("Login")
    data object Signup : Routes("SignUp")

    data object Home : Routes(
        "Home",
        "Home",
        Icons.Outlined.Home,
        Icons.Filled.Home,
        "Home"
    )

    data object Search : Routes(
        "Search",
        "Search",
        Icons.Outlined.Search,
        Icons.Filled.Search,
        "Search"
    )

    data object Favorites : Routes(
        "Favorites",
        "Favorites",
        Icons.Outlined.FavoriteBorder,
        Icons.Filled.Favorite,
        "Favorites"
    )

    data object Settings : Routes(
        "Settings",
        "Settings",
        Icons.Outlined.Settings,
        Icons.Filled.Settings,
        "Settings"
    )

    data object MovieDetails : Routes(
        "MovieDetails/{movieId}",
        "Movie Details"
    ) {
        fun createRoute(movieId: Long) = "MovieDetails/$movieId"
    }

}