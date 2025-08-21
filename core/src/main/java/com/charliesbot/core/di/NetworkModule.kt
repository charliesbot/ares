package com.charliesbot.core.di

import com.charliesbot.core.common.Constants
import com.charliesbot.core.data.remote.auth.AuthApi
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiQualifiers {
  const val AUTH_API = "AUTH_API"
  const val FEEDLY_API = "FEEDLY_API"
}

val networkModule = module {

  // Shared OkHttp Client
  single<OkHttpClient> { OkHttpClient.Builder().build() }

  // Auth Retrofit
  single<Retrofit>(named(ApiQualifiers.AUTH_API)) {
    Retrofit.Builder()
      .baseUrl(Constants.AUTH_BASE_URL)
      .client(get())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  // Feedly Retrofit (for future use)
  single<Retrofit>(named(ApiQualifiers.FEEDLY_API)) {
    Retrofit.Builder()
      .baseUrl(Constants.FEEDLY_BASE_URL)
      .client(get())
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  // Auth API
  single<AuthApi> { get<Retrofit>(named(ApiQualifiers.AUTH_API)).create(AuthApi::class.java) }
}
