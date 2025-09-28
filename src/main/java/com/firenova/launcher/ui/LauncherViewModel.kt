package com.firenova.launcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firenova.launcher.data.AppInfo
import com.firenova.launcher.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing launcher state
 * Uses StateFlow for reactive state management
 */
class LauncherViewModel(
    private val repository: AppRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    init {
        loadApps()
    }
    
    private fun loadApps() {
        viewModelScope.launch {
            try {
                val apps = repository.getAllInstalledApps()
                _uiState.value = UiState.Success(apps)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load applications: ${e.message}")
            }
        }
    }
    
    fun retry() {
        _uiState.value = UiState.Loading
        loadApps()
    }
}

/**
 * Sealed interface representing different UI states
 */
sealed interface UiState {
    object Loading : UiState
    data class Error(val message: String) : UiState
    data class Success(val apps: List<AppInfo>) : UiState
}
