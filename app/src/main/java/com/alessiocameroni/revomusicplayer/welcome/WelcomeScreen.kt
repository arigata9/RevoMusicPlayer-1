package com.alessiocameroni.revomusicplayer.welcome

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.navigation.Screens
import com.alessiocameroni.revomusicplayer.permissions.checkPermissions
import com.alessiocameroni.revomusicplayer.permissionsList
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme
import com.alessiocameroni.revomusicplayer.welcome.components.WelcomePermissionRow
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarMessage = stringResource(id = R.string.str_permStorageDenied)
    val snackBarAction = stringResource(id = R.string.str_settings)

    val buttonForwardEnabled = checkPermissions()
    val buttonStorageEnabled = remember { mutableStateOf(true) }

    val mediaPermissionState = rememberPermissionState(
        permission = permissionsList[0]
    ) { isGranted ->
        if(isGranted) { buttonStorageEnabled.value = false }
        else {
            callSnackBar(
                context = context,
                coroutineScope = scope,
                hostState = snackBarHostState,
                stringMessage = snackBarMessage,
                stringAction = snackBarAction
            )
        }
    }

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val stringRevo = stringResource(id = R.string.app_name)

                                Text(text = stringResource(id = R.string.str_heyThere))
                                Text(
                                    buildAnnotatedString {
                                        append(stringResource(id = R.string.str_welcome))
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        ) {
                                            append(" $stringRevo")
                                        }
                                    }
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        content = {
                            Button(
                                onClick = {
                                    navController.navigate(Screens.MainScreen.route) {
                                        popUpTo(Screens.WelcomeScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 15.dp)
                                    .fillMaxWidth(),
                                enabled = buttonForwardEnabled
                            ) {
                                Icon(
                                    painter =
                                    painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                                    contentDescription = stringResource(id = R.string.str_letsGo),
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .size(ButtonDefaults.IconSize)
                                )

                                Text(text = stringResource(id = R.string.str_letsGo))
                            }
                        }
                    )
                },
                snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize()
                    ) {
                        Divider()

                        WelcomePermissionRow(
                            modifier = Modifier,
                            number = 1,
                            stringMainTitle = stringResource(id = R.string.str_storageAccess),
                            stringSubtitle = stringResource(id = R.string.desc_storageAccess),
                            unitButton = {
                                FilledTonalButton(
                                    onClick = {
                                        mediaPermissionState.launchPermissionRequest()
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    enabled = buttonStorageEnabled.value
                                ) {
                                    Box {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_outlined_sd_card_24),
                                            contentDescription = stringResource(id = R.string.desc_grantAccess),
                                            modifier = Modifier
                                                .padding(end = 8.dp)
                                                .size(ButtonDefaults.IconSize)
                                        )

                                        Text(
                                            text = stringResource(id = R.string.str_grantAccess),
                                            modifier = Modifier
                                                .padding(end = 8.dp)
                                                .fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}

fun callSnackBar(
    context: Context,
    coroutineScope: CoroutineScope,
    stringMessage: String,
    stringAction: String,
    hostState: SnackbarHostState,
) {
    coroutineScope.launch {
        val snackBar = hostState.showSnackbar(
            message = stringMessage,
            actionLabel = stringAction,
            duration = SnackbarDuration.Short
        )

        when(snackBar) {
            SnackbarResult.Dismissed -> {  }
            SnackbarResult.ActionPerformed -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:com.alessiocameroni.revomusicplayer")
                context.startActivity(intent)
            }
        }
    }
}