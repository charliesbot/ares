package com.charliesbot.core.domain.usecase

import com.charliesbot.core.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
  suspend operator fun invoke(): Result<Unit> {
    return authRepository.logout()
  }
}
