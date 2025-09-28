package com.firenova.launcher.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.GridCells
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.firenova.launcher.data.AppInfo
import com.firenova.launcher.ui.components.AppCard

/**
 * Main launcher screen with TV-optimized grid layout
 * Uses TvLazyVerticalGrid for optimal TV navigation
 */
@Composable
fun LauncherScreen(
    uiState: UiState,
    onAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (uiState) {
            is UiState.Loading -> {
                Text(
                    text = "Loading applications...",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            is UiState.Error -> {
                Text(
                    text = uiState.message,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            is UiState.Success -> {
                TvLazyVerticalGrid(
                    columns = GridCells.Fixed(6), // 6 columns for TV layout
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.apps) { app ->
                        AppCard(
                            app = app,
                            onClick = { onAppClick(app) }
                        )
                    }
                }
            }
        }
    }
}
