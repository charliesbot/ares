package com.charliesbot.core.di

import org.koin.core.module.Module

val coreModule: List<Module> = listOf(networkModule, authModule)
