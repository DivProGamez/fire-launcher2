package com.firenova.launcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.firenova.launcher.repository.AppRepository

/**
 * Factory for creating LauncherViewModel with dependencies
 */
class LauncherViewModelFactory(
    private val repository: AppRepository
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LauncherViewModel::class.java)) {
            return LauncherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
