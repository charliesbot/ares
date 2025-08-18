package com.charliesbot.core.di

import com.charliesbot.core.data.local.auth.TokenManager
import com.charliesbot.core.data.repository.AuthRepositoryImpl
import com.charliesbot.core.domain.repository.AuthRepository
import com.charliesbot.core.domain.usecase.IsUserLoggedInUseCase
import com.charliesbot.core.domain.usecase.LoginUseCase
import com.charliesbot.core.domain.usecase.LogoutUseCase
import com.charliesbot.core.domain.usecase.RefreshTokenUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authModule = module {

  // Token Manager (local storage)
  single<TokenManager> { TokenManager(androidContext()) }

  // Repository
  single<AuthRepository> {
    AuthRepositoryImpl(
      authApi = get(), // Koin resolves AuthApi automatically
      tokenManager = get(), // Koin resolves TokenManager automatically
    )
  }

  // Use Cases
  single<LoginUseCase> {
    LoginUseCase(get()) // Koin resolves AuthRepository automatically
  }

  single<RefreshTokenUseCase> { RefreshTokenUseCase(get()) }

  single<IsUserLoggedInUseCase> { IsUserLoggedInUseCase(get()) }

  single<LogoutUseCase> { LogoutUseCase(get()) }
}
