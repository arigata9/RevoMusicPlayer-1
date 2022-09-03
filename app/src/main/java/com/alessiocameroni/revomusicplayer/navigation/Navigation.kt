package com.alessiocameroni.revomusicplayer.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.revomusicplayer.library.albums.AlbumsScreen
import com.alessiocameroni.revomusicplayer.library.artists.ArtistsScreen
import com.alessiocameroni.revomusicplayer.library.playlists.main.PlaylistsScreen
import com.alessiocameroni.revomusicplayer.library.playlists.playlistview.PlaylistViewScreen
import com.alessiocameroni.revomusicplayer.library.spotify.SpotifyFavoritesScreen
import com.alessiocameroni.revomusicplayer.library.songs.TracksScreen
import com.alessiocameroni.revomusicplayer.main.MainScreen
import com.alessiocameroni.revomusicplayer.player.PlayerScreen
import com.alessiocameroni.revomusicplayer.search.SearchScreen
import com.alessiocameroni.revomusicplayer.settings.about.AboutScreen
import com.alessiocameroni.revomusicplayer.settings.customization.LooksScreen
import com.alessiocameroni.revomusicplayer.settings.main.SettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(
            route = Screens.MainScreen.route,
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "search_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "player_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "search_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "player_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            }
        ) { MainScreen(navController = navController) }

        composable(
            route = Screens.PlayerScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "main_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "main_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))

                    // From screen
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // To screen
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            }
        ) { PlayerScreen(navController = navController) }

        composable(
            route = Screens.SearchScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "main_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "main_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    else -> null
                }
            },
            //TODO Add popEnter when actually listing items
        ) { SearchScreen(navController = navController) }

        composable(
            route = Screens.SettingsScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "main_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "player_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "main_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "player_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))

                    // From screen
                    "customization_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    "about_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    //From screen
                    "customization_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "about_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { -100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            }
        ) { SettingsScreen(navController = navController) }

        // Settings SubScreens
        composable(
            route = SettingsScreens.CustomizationScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    //From screen
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    //To screen
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            }
            //TODO Add popEnter when actually listing items
        ) { LooksScreen(navController = navController) }

        composable(
            route = SettingsScreens.AboutScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "settings_screen" ->
                        slideInHorizontally(
                            initialOffsetX = { 100 },
                            animationSpec = tween( 200 )
                        ) + fadeIn(animationSpec = tween( 200 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "settings_screen" ->
                        slideOutHorizontally(
                            targetOffsetX = { 100 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            }
            //TODO Add popEnter when actually listing items
        ) { AboutScreen(navController = navController) }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationBottomNavBar(
    navControllerBottomBar: NavHostController,
    navControllerApp: NavController
) {
    AnimatedNavHost(navController = navControllerBottomBar, startDestination = "tracks") {
        composable(
            route = "tracks",
            enterTransition = {
                when(initialState.destination.route) {
                    "tracks" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "tracks" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    else -> null
                }
            }
        ) { TracksScreen(navController = navControllerApp) }

        composable(
            route = "albums",
            enterTransition = {
                when(initialState.destination.route) {
                    "tracks" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "tracks" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    else -> null
                }
            }
        ) { AlbumsScreen(navController = navControllerApp) }

        composable(
            route = "artists",
            enterTransition = {
                when(initialState.destination.route) {
                    "tracks" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "tracks" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    else -> null
                }
            }
        ) { ArtistsScreen(navController = navControllerApp) }

        composable(
            route = "playlists",
            enterTransition = {
                when(initialState.destination.route) {
                    // To screen
                    "tracks" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))

                    //From screen
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "tracks" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    "playlist_view_screen" ->
                        slideOutVertically (
                            targetOffsetY = { -30 },
                            animationSpec = tween( 210 )
                        ) + fadeOut(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            popEnterTransition = {
                when(initialState.destination.route) {
                    // From screen
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            }
        ) {
            PlaylistsScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar
            )
        }

        // Playlist SubScreens
        composable(
            route = PlaylistsScreens.PlaylistViewScreen.route,
            enterTransition = {
                when(initialState.destination.route) {
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    // To screen
                    "tracks" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "albums" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "artists" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "playlists" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    "spotify" ->
                        slideOutVertically(
                            targetOffsetY = { -30 },
                            animationSpec = tween(210)
                        ) + fadeOut(animationSpec = tween(210))
                    else -> null
                }
            }
        ) { PlaylistViewScreen(
                navController = navControllerApp,
                navControllerBottomBar = navControllerBottomBar
            )
        }


        composable(
            route = "spotify",
            enterTransition = {
                when(initialState.destination.route) {
                    "tracks" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "albums" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "artists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlists" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "playlist_view_screen" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    "spotify" ->
                        slideInVertically (
                            initialOffsetY = { 30 },
                            animationSpec = tween( 210 )
                        ) + fadeIn(animationSpec = tween( 210 ))
                    else -> null
                }
            },
            exitTransition = {
                when(targetState.destination.route) {
                    "tracks" -> fadeOut(animationSpec = tween( 100 ))
                    "albums" -> fadeOut(animationSpec = tween( 100 ))
                    "artists" -> fadeOut(animationSpec = tween( 100 ))
                    "playlists" -> fadeOut(animationSpec = tween( 100 ))
                    "spotify" -> fadeOut(animationSpec = tween( 100 ))
                    else -> null
                }
            }
        ) { SpotifyFavoritesScreen(navController = navControllerApp) }
    }
}