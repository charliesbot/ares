package com.charliesbot.features.auth.di

import com.charliesbot.features.auth.AuthViewModel
import com.charliesbot.core.domain.usecase.LoginUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authFeatureModule = module { viewModel { AuthViewModel(get<LoginUseCase>()) } }
