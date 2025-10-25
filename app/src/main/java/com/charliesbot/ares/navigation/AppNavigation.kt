package com.charliesbot.ares.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.charliesbot.features.auth.LoginScreen
import com.charliesbot.features.feed.FeedScreen

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Public route
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        // Protected routes (nested graph)
        navigation(startDestination = Routes.FEED, route = Routes.AUTHENTICATED) {
            composable(Routes.FEED) {
                FeedScreen(navController)
            }
            // Future protected routes
            // composable(Routes.ARTICLE) { ArticleScreen(navController) }
            // composable(Routes.SETTINGS) { SettingsScreen(navController) }
        }
    }
}
