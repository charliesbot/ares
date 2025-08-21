package com.charliesbot.ares.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.charliesbot.core.domain.usecase.IsUserLoggedInUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppBackStack(startRoute: Route, private val loginRoute: Route): KoinComponent {
  interface RequiresAuth

  private val isUserLoggedInUseCase: IsUserLoggedInUseCase by inject()
  val backStack = mutableStateListOf(startRoute)

  private var onLoginSuccessRoute: Route? by mutableStateOf(null)


  suspend fun isLoggedIn(): Boolean = isUserLoggedInUseCase()

  suspend fun add(route: Route) {
    // Conditional navigation logic:
    // If user tries to access a protected route while not logged in,
    // redirect them to login and remember where they wanted to go
    if (route is RequiresAuth && !isLoggedIn()) {
      onLoginSuccessRoute = route // Remember their intended destination
      backStack.add(Route.Login) // Redirect to login screen
    } else {
      backStack.add(route) // Normal navigation
    }
  }

  fun remove() = backStack.removeLastOrNull()

  fun login() {
    onLoginSuccessRoute?.let {
      backStack.add(it)
      backStack.remove(loginRoute)
    }
  }

  fun logout() {
    backStack.removeAll { it is RequiresAuth }
  }
}
