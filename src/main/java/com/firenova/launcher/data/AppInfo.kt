package com.firenova.launcher.data

/**
 * Data class representing an installed application
 * Contains only essential metadata for performance optimization
 */
data class AppInfo(
    val packageName: String,
    val label: String,
    val isSystemApp: Boolean
)
