package com.charliesbot.core.data.remote.auth

import com.charliesbot.core.data.remote.auth.dto.AuthResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {
  @GET("exchange_token")
  suspend fun exchangeToken(@Query("code") code: String): Response<AuthResponse>

  @GET("refresh_token")
  suspend fun refreshToken(@Query("refresh_token") refreshToken: String): Response<AuthResponse>
}
