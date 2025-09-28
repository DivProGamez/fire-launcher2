package com.firenova.launcher

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme as TvMaterialTheme
import com.firenova.launcher.data.AppInfo
import com.firenova.launcher.repository.AppRepository
import com.firenova.launcher.ui.LauncherScreen
import com.firenova.launcher.ui.LauncherViewModel

/**
 * Main activity for FireNova Launcher
 * Optimized for Fire TV with D-Pad navigation
 */
@OptIn(ExperimentalTvMaterial3Api::class)
class MainActivity : ComponentActivity() {
    
    private val viewModel: LauncherViewModel by viewModels {
        LauncherViewModelFactory(AppRepository(this))
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            TvMaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by viewModel.uiState.collectAsState()
                    
                    LauncherScreen(
                        uiState = uiState,
                        onAppClick = { app -> launchApp(app) }
                    )
                }
            }
        }
    }
    
    /**
     * Launches the selected application
     * Handles cases where the app cannot be launched
     */
    private fun launchApp(app: AppInfo) {
        try {
            val launchIntent = packageManager.getLaunchIntentForPackage(app.packageName)
            if (launchIntent != null) {
                startActivity(launchIntent)
            } else {
                showErrorToast("Cannot launch ${app.label}")
            }
        } catch (e: Exception) {
            showErrorToast("Failed to launch ${app.label}: ${e.message}")
        }
    }
    
    /**
     * Shows error toast message
     */
    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
