package com.charliesbot.ares.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation3.BackStack
import com.charliesbot.core.domain.usecase.IsUserLoggedInUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppBackStack : KoinComponent {
    
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase by inject()
    private val backStack = BackStack(Route.Login)
    
    private var onLoginSuccessRoute: Route? by mutableStateOf(null)
    
    val isLoggedIn: Boolean
        get() = isUserLoggedInUseCase()
    
    fun add(route: Route) {
        if (route is RequiresAuth && !isLoggedIn) {
            onLoginSuccessRoute = route
            backStack.replaceAll(Route.Login)
        } else {
            backStack.add(route)
        }
    }
    
    fun login() {
        val destinationRoute = onLoginSuccessRoute ?: Route.Feed
        onLoginSuccessRoute = null
        backStack.replaceAll(destinationRoute)
    }
    
    fun logout() {
        backStack.replaceAll(Route.Login)
    }
    
    fun goBack(): Boolean {
        return if (backStack.canGoBack) {
            backStack.pop()
            true
        } else {
            false
        }
    }
    
    fun getBackStack(): BackStack<Route> = backStack
}