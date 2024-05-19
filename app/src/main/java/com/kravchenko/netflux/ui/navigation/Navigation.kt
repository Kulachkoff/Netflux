package com.kravchenko.netflux.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.kravchenko.netflux.ui.favorites.FavoritesScreen
import com.kravchenko.netflux.ui.home.HomeScreen
import com.kravchenko.netflux.ui.home.HomeViewModel
import com.kravchenko.netflux.ui.landing.LandingScreen
import com.kravchenko.netflux.ui.login.LoginScreen
import com.kravchenko.netflux.ui.movie.MovieDetailsScreen
import com.kravchenko.netflux.ui.movie.MovieDetailsViewModel
import com.kravchenko.netflux.ui.search.SearchScreen
import com.kravchenko.netflux.ui.settings.SettingsScreen
import com.kravchenko.netflux.ui.signup.SignUpScreen

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val pages = listOf(Routes.Home, Routes.Search, Routes.Favorites, Routes.Settings)
    var selectedPage by rememberSaveable { mutableStateOf(Routes.Home.route) }

    LaunchedEffect(currentRoute) {
        if (currentRoute in pages.map { it.route }) {
            selectedPage = currentRoute ?: Routes.Home.route
        }
    }

    Scaffold(
        topBar = {
            when (currentRoute) {
                Routes.Search.route -> CustomTopAppBar(title = Routes.Search.name!!)
                Routes.Favorites.route -> CustomTopAppBar(title = Routes.Favorites.name!!)
                Routes.Settings.route -> CustomTopAppBar(title = Routes.Settings.name!!)
                Routes.MovieDetails.route -> CustomTopAppBar(title = Routes.MovieDetails.name!!, showBackButton = true) {
                    navController.navigateUp()
                }
                else -> {}
            }
        },
        bottomBar = {
            if (currentRoute in pages.map { it.route }) {
                CustomBottomAppBar(
                    pages = pages,
                    selectedPage = selectedPage,
                    onPageClicked = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        content = { padding ->
            NavHost(
                navController = navController,
                startDestination = Routes.Auth.route,
                modifier = Modifier.padding(padding)
            ) {
                navigation(startDestination = Routes.Landing.route, route = Routes.Auth.route) {
                    composable(Routes.Landing.route) {
                        LandingScreen(navController, hiltViewModel())
                    }
                    composable(Routes.Login.route) {
                        LoginScreen(navController)
                    }
                    composable(Routes.Signup.route) {
                        SignUpScreen(navController)
                    }
                }
                navigation(startDestination = Routes.Home.route, route = Routes.Main.route) {
                    composable(Routes.Home.route) {
                        HomeScreen(navController, hiltViewModel())
                    }
                    composable(Routes.Search.route) {
                        SearchScreen(navController)
                    }
                    composable(Routes.Favorites.route) {
                        FavoritesScreen(navController)
                    }
                    composable(Routes.Settings.route) {
                        SettingsScreen(navController)
                    }
                    composable(
                        route = Routes.MovieDetails.route,
                        arguments = listOf(navArgument("movieId") { type = NavType.LongType })
                    ) { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getLong("movieId") ?: 0L
                        MovieDetailsScreen(hiltViewModel(), movieId)
                    }
                }
            }
        }
    )
}
