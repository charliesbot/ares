package com.charliesbot.core.domain.usecase

import com.charliesbot.core.domain.model.AuthSession
import com.charliesbot.core.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
  suspend operator fun invoke(code: String): Result<AuthSession> {
    if (code.isBlank()) {
      return Result.failure(IllegalArgumentException("Authorization code cannot be empty"))
    }

    return authRepository.exchangeToken(code)
  }
}
