package com.charliesbot.core.data.repository

import com.charliesbot.core.data.local.auth.TokenManager
import com.charliesbot.core.data.remote.auth.AuthApi
import com.charliesbot.core.data.remote.auth.dto.toDomain
import com.charliesbot.core.domain.model.AuthSession
import com.charliesbot.core.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authApi: AuthApi, private val tokenManager: TokenManager) :
  AuthRepository {

  override suspend fun exchangeToken(code: String): Result<AuthSession> {
    return try {
      val response = authApi.exchangeToken(code)
      if (response.isSuccessful && response.body() != null) {
        val authSession = response.body()!!.toDomain()
        tokenManager.saveSession(authSession)
        Result.success(authSession)
      } else {
        Result.failure(Exception("Token exchange failed: ${response.message()}"))
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun refreshToken(): Result<AuthSession> {
    return try {
      val currentSession =
        tokenManager.getCurrentSession()
          ?: return Result.failure(Exception("No refresh token available"))

      val response = authApi.refreshToken(currentSession.refreshToken)
      if (response.isSuccessful && response.body() != null) {
        val newSession = response.body()!!.toDomain()
        tokenManager.saveSession(newSession)
        Result.success(newSession)
      } else {
        Result.failure(Exception("Token refresh failed: ${response.message()}"))
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun getCurrentSession(): AuthSession? {
    return tokenManager.getCurrentSession()
  }

  override suspend fun isUserLoggedIn(): Boolean {
    return tokenManager.isLoggedIn()
  }

  override suspend fun logout(): Result<Unit> {
    return try {
      tokenManager.clearSession()
      Result.success(Unit)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}
