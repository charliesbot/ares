package com.charliesbot.features.auth

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.charliesbot.core.common.Constants

@Composable
fun LoginScreen() {
  val context = LocalContext.current

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
    ) {
      Text("Login with Feedly")
    }
  }
}
