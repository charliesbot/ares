package com.charliesbot.core.domain.usecase

import com.charliesbot.core.domain.repository.AuthRepository

class IsUserLoggedInUseCase(private val authRepository: AuthRepository) {
  suspend operator fun invoke(): Boolean {
    return authRepository.isUserLoggedIn()
  }
}
