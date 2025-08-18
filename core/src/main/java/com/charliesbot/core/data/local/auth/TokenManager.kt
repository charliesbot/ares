package com.charliesbot.core.data.local.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.charliesbot.core.common.extensions.dataStore
import com.charliesbot.core.domain.model.AuthSession
import kotlinx.coroutines.flow.first

class TokenManager(private val context: Context) {

  private val dataStore = context.dataStore

  private object PreferencesKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val EXPIRES_AT = stringPreferencesKey("expires_at")
    val FEEDLY_PLAN = stringPreferencesKey("feedly_plan")
  }

  suspend fun saveSession(session: AuthSession) {
    dataStore.edit { preferences ->
      preferences[PreferencesKeys.ACCESS_TOKEN] = session.accessToken
      preferences[PreferencesKeys.REFRESH_TOKEN] = session.refreshToken
      preferences[PreferencesKeys.EXPIRES_AT] = session.expiresAt
      preferences[PreferencesKeys.FEEDLY_PLAN] = session.feedlyPlan
    }
  }

  suspend fun getCurrentSession(): AuthSession? {
    val preferences = dataStore.data.first()
    val accessToken = preferences[PreferencesKeys.ACCESS_TOKEN] ?: return null
    val refreshToken = preferences[PreferencesKeys.REFRESH_TOKEN] ?: return null
    val expiresAt = preferences[PreferencesKeys.EXPIRES_AT] ?: return null
    val feedlyPlan = preferences[PreferencesKeys.FEEDLY_PLAN] ?: return null

    return AuthSession(
      accessToken = accessToken,
      refreshToken = refreshToken,
      expiresAt = expiresAt,
      feedlyPlan = feedlyPlan,
    )
  }

  suspend fun clearSession() {
    dataStore.edit { preferences -> preferences.clear() }
  }

  suspend fun isLoggedIn(): Boolean {
    return getCurrentSession() != null
  }
}
