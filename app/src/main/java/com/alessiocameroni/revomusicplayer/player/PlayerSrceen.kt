package com.alessiocameroni.revomusicplayer.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.player.components.LeftSongControls
import com.alessiocameroni.revomusicplayer.ui.theme.RevoMusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(navController: NavController) {
    var checked by remember { mutableStateOf(false) }

    RevoMusicPlayerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    SmallTopAppBar(
                        title = { Text(text = "") },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    painter = painterResource(
                                        id = R.drawable.ic_baseline_keyboard_arrow_down_24
                                    ),
                                    contentDescription = stringResource(
                                        id = R.string.desc_closemusic
                                    )
                                )
                            }
                        }
                    )
                },
            ) { padding ->
                val constraints = ConstraintSet {
                    val albumCover = createRefFor("AlbumCover")
                    val songInformation = createRefFor("SongInformation")
                    val songControls = createRefFor("SongControls")

                    constrain(albumCover) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(songInformation.top)
                    }

                    constrain(songInformation) {
                        start.linkTo(parent.start)
                        top.linkTo(albumCover.bottom)
                        end.linkTo(parent.end)
                    }

                    constrain(songControls) {
                        start.linkTo(parent.start)
                        top.linkTo(songInformation.bottom)
                        end.linkTo(parent.end)
                    }
                }

                ConstraintLayout(
                    constraints, modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Box(
                        modifier = Modifier
                            .layoutId("AlbumCover")
                            .padding(20.dp)
                            .size(340.dp)
                            .clip(MaterialTheme.shapes.extraLarge)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {

                    }

                    Box(
                        modifier = Modifier
                            .layoutId("SongInformation")
                            .padding(horizontal = 20.dp)
                            .size(340.dp, 50.dp)
                    ) {
                        val songInfoConstraints = ConstraintSet {
                            val songNameText = createRefFor("SongNameText")
                            val artistNameText = createRefFor("ArtistNameText")
                            val favoriteIconButton = createRefFor("FavoriteIconButton")

                            constrain(songNameText) {
                                start.linkTo(parent.start)
                                top.linkTo(parent.top)
                                bottom.linkTo(artistNameText.top)
                            }

                            constrain(artistNameText) {
                                start.linkTo(parent.start)
                                top.linkTo(songNameText.bottom)
                                bottom.linkTo(parent.bottom)
                            }

                            constrain(favoriteIconButton) {
                                top.linkTo(parent.top)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                        }

                        ConstraintLayout(
                            songInfoConstraints,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                modifier = Modifier
                                    .layoutId("SongNameText")
                                    .width(280.dp),
                                text = "SongName",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.headlineSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                modifier = Modifier
                                    .layoutId("ArtistNameText")
                                    .width(280.dp),
                                text = "ArtistName",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            IconToggleButton(
                                modifier = Modifier
                                    .layoutId("FavoriteIconButton"),
                                checked = checked,
                                onCheckedChange = { checked = it }
                            ) {
                                if(checked) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_filled_favorite_24),
                                        contentDescription = stringResource(id = R.string.desc_favorite)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_outlined_favorite_24),
                                        contentDescription = stringResource(id = R.string.desc_favorite)
                                    )
                                }
                            }
                        }
                    }

                    LeftSongControls(
                        navController = navController,
                        modifier = Modifier
                            .layoutId("SongControls")
                            .padding(20.dp)
                            .width(340.dp),
                        floatPosition = 0.5f,
                        boolShuffleChecked = false,
                        boolRepeatChecked = false
                    )
                }
            }
        }
    }
}