package com.alessiocameroni.revomusicplayer.permissions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.alessiocameroni.revomusicplayer.data.navigation.Navigation
import com.alessiocameroni.revomusicplayer.permissionsList
import com.google.accompanist.permissions.*

@Composable
fun SetContentByPermission() {
    val allPermissionsAvailable = checkPermissions()
    val enterApp = remember { allPermissionsAvailable }

    when {
        enterApp -> Navigation(startDestination = "main_screen")
        else -> Navigation(startDestination = "welcome_screen")
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun checkPermissions(): Boolean {
    val permissionState = rememberMultiplePermissionsState(permissions = permissionsList)

    permissionState.permissions.forEach {
        when (it.permission) {
            permissionsList[0] -> {
                when { !it.status.isGranted -> return false }
            }
        }
    }
    return true
}