package com.charliesbot.core.data.remote.auth.dto

import com.charliesbot.core.domain.model.AuthSession

fun AuthResponse.toDomain(): AuthSession {
    return AuthSession(
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresAt = expiresAt,
        feedlyPlan = feedlyPlan
    )
}
