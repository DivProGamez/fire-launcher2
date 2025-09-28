package com.firenova.launcher.repository

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import com.firenova.launcher.data.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for managing installed applications
 * Optimized for performance with background execution
 */
class AppRepository(private val context: Context) {
    
    private val packageManager: PackageManager = context.packageManager
    
    /**
     * Retrieves all installed applications that can be launched
     * CRITICAL: Uses ACTION_MAIN + CATEGORY_LAUNCHER to include sideloaded apps
     * Avoids CATEGORY_LEANBACK_LAUNCHER to prevent hiding sideloaded applications
     */
    suspend fun getAllInstalledApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        
        val resolveInfoList: List<ResolveInfo> = packageManager.queryIntentActivities(
            intent,
            PackageManager.GET_META_DATA
        )
        
        resolveInfoList.mapNotNull { resolveInfo ->
            try {
                val packageName = resolveInfo.activityInfo.packageName
                val label = resolveInfo.loadLabel(packageManager).toString()
                
                // Determine if it's a system app
                val isSystemApp = isSystemApp(packageName)
                
                AppInfo(
                    packageName = packageName,
                    label = label,
                    isSystemApp = isSystemApp
                )
            } catch (e: Exception) {
                // Skip apps that can't be processed
                null
            }
        }.sortedBy { it.label }
    }
    
    /**
     * Determines if an app is a system app
     */
    private fun isSystemApp(packageName: String): Boolean {
        return try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            packageInfo.applicationInfo.flags and android.content.pm.ApplicationInfo.FLAG_SYSTEM != 0
        } catch (e: Exception) {
            false
        }
    }
}
