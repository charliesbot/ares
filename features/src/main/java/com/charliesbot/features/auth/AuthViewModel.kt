package com.charliesbot.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charliesbot.core.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
  val isLoading: Boolean = false,
  val isLoggedIn: Boolean = false,
  val errorMessage: String? = null,
)

class AuthViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

  private val _uiState = MutableStateFlow(AuthUiState())
  val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

  fun handleOAuthCallback(code: String) {
    // OAuth flow completion:
    // 1. Exchange authorization code for access token
    // 2. Save token via TokenManager
    // 3. Update UI state to trigger navigation
    viewModelScope.launch {
      _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

      loginUseCase(code)
        .fold(
          onSuccess = {
            _uiState.value =
              _uiState.value.copy(
                isLoading = false,
                isLoggedIn = true, // This triggers LaunchedEffect in AppNavigation
              )
          },
          onFailure = { error ->
            _uiState.value =
              _uiState.value.copy(isLoading = false, errorMessage = error.message ?: "Login failed")
          },
        )
    }
  }

  fun clearError() {
    _uiState.value = _uiState.value.copy(errorMessage = null)
  }
}
