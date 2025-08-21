package com.charliesbot.ares.navigation

import androidx.compose.runtime.*
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.charliesbot.features.auth.AuthViewModel
import com.charliesbot.features.auth.LoginScreen
import com.charliesbot.features.feed.FeedScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AppNavigation() {
    val appBackStack: AppBackStack = koinInject()
    val authViewModel: AuthViewModel = koinViewModel()
    val authState by authViewModel.uiState.collectAsState()

    // Bridge between OAuth completion and navigation:
    // When OAuth succeeds in AuthViewModel, trigger post-login navigation in AppBackStack
    // This will navigate the user to their originally intended destination (e.g., Feed)
    LaunchedEffect(authState.isLoggedIn) {
        if (authState.isLoggedIn) {
            appBackStack.login() // Note: This navigates AWAY from login screen to intended destination
        }
    }

    NavDisplay(
        backStack = appBackStack.backStack,
        onBack = { appBackStack.remove() },
        entryProvider =
            entryProvider {
                entry<Route.Login> { LoginScreen() }

                entry<Route.Feed> { FeedScreen() }
            },
    )
}
