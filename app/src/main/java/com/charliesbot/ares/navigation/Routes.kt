package com.charliesbot.ares.navigation

import kotlinx.serialization.Serializable

interface RequiresAuth

@Serializable
sealed class Route {
    
    @Serializable
    data object Login : Route()
    
    @Serializable
    data object Feed : Route(), RequiresAuth
}