package com.charliesbot.ares.di

import com.charliesbot.ares.navigation.AppBackStack
import com.charliesbot.ares.navigation.Route
import org.koin.dsl.module

val navigationModule = module {
    single {
        AppBackStack(
            startRoute = Route.Feed,
            loginRoute = Route.Login
        )
    }
}
