package com.charliesbot.core.domain.model

data class AuthSession(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: String,
    val feedlyPlan: String,
    val isExpired: Boolean = false
)
