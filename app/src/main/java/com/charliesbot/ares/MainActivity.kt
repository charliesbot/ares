package com.charliesbot.ares

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.charliesbot.ares.navigation.AppNavigation
import com.charliesbot.ares.ui.theme.AresTheme
import com.charliesbot.core.common.Constants
import com.charliesbot.features.auth.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

  private val authViewModel: AuthViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    handleIntent(intent)

    setContent {
      AresTheme { Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding -> AppNavigation() } }
    }
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    handleIntent(intent)
  }

  private fun handleIntent(intent: Intent) {
    // OAuth callback handler:
    // When Chrome Custom Tab redirects back with charliesbotrssapp://oauth?code=...
    val data: Uri? = intent.data
    if (
      data != null &&
        data.scheme == Constants.OAUTH_REDIRECT_SCHEME &&
        data.host == Constants.OAUTH_REDIRECT_HOST
    ) {

      val code = data.getQueryParameter("code") // Extract OAuth authorization code
      if (code != null) {
        authViewModel.handleOAuthCallback(code) // Process the code to get access token
      }
    }
  }
}
