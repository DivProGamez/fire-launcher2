package com.firenova.launcher.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.firenova.launcher.data.AppInfo

/**
 * Composable for displaying an application card
 * Optimized for TV navigation with D-Pad support
 */
@Composable
fun AppCard(
    app: AppInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .size(120.dp, 140.dp),
        colors = CardDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        scale = CardDefaults.scale(
            focusedScale = 1.1f
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App icon with Coil for efficient loading
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(app.packageName)
                    .crossfade(true)
                    .build(),
                contentDescription = app.label,
                modifier = Modifier.size(64.dp)
            )
            
            // App label
            Text(
                text = app.label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
