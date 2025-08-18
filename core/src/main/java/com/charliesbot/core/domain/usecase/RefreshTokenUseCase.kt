package com.charliesbot.core.domain.usecase

import com.charliesbot.core.domain.model.AuthSession
import com.charliesbot.core.domain.repository.AuthRepository

class RefreshTokenUseCase(private val authRepository: AuthRepository) {
  suspend operator fun invoke(): Result<AuthSession> {
    return authRepository.refreshToken()
  }
}
