package com.charliesbot.core.domain.repository

import com.charliesbot.core.domain.model.AuthSession

interface AuthRepository {
  suspend fun exchangeToken(code: String): Result<AuthSession>

  suspend fun refreshToken(): Result<AuthSession>

  suspend fun getCurrentSession(): AuthSession?

  suspend fun isUserLoggedIn(): Boolean

  suspend fun logout(): Result<Unit>
}
