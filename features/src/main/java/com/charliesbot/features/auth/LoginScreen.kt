package com.charliesbot.features.auth

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.charliesbot.core.common.Constants
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navController: NavController) {
  val context = LocalContext.current
  val authViewModel: AuthViewModel = koinViewModel()
  val authState by authViewModel.uiState.collectAsState()

  // Navigate to authenticated graph after successful login
  LaunchedEffect(authState.isLoggedIn) {
    if (authState.isLoggedIn) {
      navController.navigate("authenticated") {
        popUpTo("login") { inclusive = true }
      }
    }
  }

  Column(
    modifier = Modifier.fillMaxSize().padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = "Welcome to RSS App",
      style = MaterialTheme.typography.headlineMedium,
      modifier = Modifier.padding(bottom = 32.dp),
    )

    Button(
      onClick = {
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(context, Constants.OAUTH_URL.toUri())
      },
      modifier = Modifier.fillMaxWidth(),
      enabled = !authState.isLoading,
    ) {
      Text("Login with Feedly")
    }

    if (authState.isLoading) {
      Spacer(modifier = Modifier.height(16.dp))
      CircularProgressIndicator()
    }

    authState.errorMessage?.let { error ->
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = error,
        color = Color.Red,
        style = MaterialTheme.typography.bodyMedium,
      )
    }
  }
}
