package com.alessiocameroni.revomusicplayer.ui.screens.library.artistScreen.artistViewScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.ArtistAlbumData
import com.alessiocameroni.revomusicplayer.data.classes.ArtistSongData
import com.alessiocameroni.revomusicplayer.ui.navigation.NavigationScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistViewScreen(
    artistId: Long,
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: ArtistViewViewModel = viewModel()
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberLazyListState()
    val textVisibility =
        remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }
    val artistAlbums = viewModel.artistAlbums
    val artistSongs = viewModel.artistSongs
    val albumRowVisibility = viewModel.albumAmountCheck

    LaunchedEffect(Unit) {
        viewModel.initializeArtistAlbumList(context, artistId)
        viewModel.initializeArtistSongList(context, artistId)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArtistViewTopActionBar(
                navController = navController,
                navControllerBottomBar = navControllerBottomBar,
                artistName = viewModel.artist.value,
                artistAlbumAmount = viewModel.artistAlbumAmount,
                artistSongAmount = viewModel.artistSongAmount,
                scrollBehavior = scrollBehavior,
                textVisibility = textVisibility
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                state = scrollState
            ) {
                item {
                    ArtistViewHeader(
                        artistName = viewModel.artist.value,
                        artistAlbumAmount = viewModel.artistAlbumAmount,
                        artistSongAmount = viewModel.artistSongAmount,
                        leadingUnit = {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(viewModel.artistPictureUri.value)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = stringResource(id = R.string.desc_albumImage),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    )
                }

                item {
                    Log.d("ArtistViewScreen", "$albumRowVisibility")

                    AnimatedVisibility(
                        visible = albumRowVisibility.value ?: false,
                        enter = fadeIn()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            ArtistViewSectionTitle(
                                stringTitle = stringResource(id = R.string.str_albums)
                            )

                            RowAlbumList(
                                artistAlbums,
                                navControllerBottomBar
                            )
                        }
                    }
                }

                item {
                    ArtistViewSectionTitle(
                        stringTitle = stringResource(id = R.string.str_songs)
                    )
                }

                artistSongList(
                    artistSongs
                )
            }
        }
    )
}

@Composable
fun RowAlbumList(
    artistAlbums: MutableList<ArtistAlbumData>,
    navControllerBottomBar: NavHostController
) {
    LazyRow(
        contentPadding = PaddingValues(start = 5.dp)
    ) {
        itemsIndexed(items = artistAlbums) { _, item ->
            Column(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .clickable {
                        navControllerBottomBar.navigate(
                            NavigationScreens.AlbumViewScreen.route +
                                    "/${item.albumId}"
                        )
                    },
            ) {
                ArtistViewHorizontalListItem(
                    albumTitle = item.albumTitle
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.albumCoverUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = stringResource(id = R.string.desc_albumImage),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

private fun LazyListScope.artistSongList(
    artistSongs: MutableList<ArtistSongData>
) {
    itemsIndexed(items = artistSongs) { _, item ->
        Row(
            modifier = Modifier
                .clickable {  },
        ) {
            PixelyListItem(
                headlineTextString = item.songTitle,
                largeHeadline = false,
                maxHeadlineLines = 1,
                supportingTextString = item.album,
                maxSupportingLines = 1,
                leadingContent = {
                    Text(
                        text = item.track ?: "-",
                        modifier = Modifier
                            .widthIn(max = 40.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                trailingContent = {
                    val expandedItemMenu = remember { mutableStateOf(false) }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.fixedDuration ?: "00:00",
                            maxLines = 1,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                            IconButton(onClick = { expandedItemMenu.value = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                    contentDescription = stringResource(id = R.string.str_moreOptions)
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}