package com.charliesbot.ares.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

  @Serializable data object Login : Route()

  @Serializable data object Feed : AppBackStack.RequiresAuth
}