package com.charliesbot.core.data.remote.auth.dto

import com.google.gson.annotations.SerializedName

data class AuthResponse(
  @SerializedName("access_token") val accessToken: String,
  @SerializedName("refresh_token") val refreshToken: String,
  @SerializedName("expires_in") val expiresIn: String,
  @SerializedName("expires_at") val expiresAt: String,
  @SerializedName("feedly_plan") val feedlyPlan: String,
)
