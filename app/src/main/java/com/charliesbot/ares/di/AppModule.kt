package com.charliesbot.ares.di

import com.charliesbot.core.di.coreModule
import com.charliesbot.features.auth.di.authFeatureModule
import org.koin.core.module.Module

val appModule: List<Module> = coreModule + authFeatureModule