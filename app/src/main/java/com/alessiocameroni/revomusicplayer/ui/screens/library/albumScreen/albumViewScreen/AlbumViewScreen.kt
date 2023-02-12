package com.alessiocameroni.revomusicplayer.ui.screens.library.albumScreen.albumViewScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.ui.screens.library.ItemDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewScreen(
    albumId: Long,
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumViewViewModel = viewModel(),
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberLazyListState()
    val textVisibility = remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0
        }
    }
    val albumSongs = viewModel.albumSongs

    LaunchedEffect(Unit) {
        viewModel.initializeAlbumSongsList(context, albumId)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopActionBar(
                navControllerBottomBar = navControllerBottomBar,
                albumTitleString = viewModel.albumTitle.toString(),
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
                    AlbumViewHeader(
                        albumTitleString = viewModel.albumTitle.value,
                        albumArtistString = viewModel.artist.value,
                        albumInfoString = null,
                        leadingUnit = {
                            /*AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(viewModel.albumCoverUri)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = stringResource(id = R.string.desc_albumImage)
                            )*/
                        }
                    )
                }

                item {
                    AlbumViewSectionTitle(
                        stringTitle = stringResource(id = R.string.str_songs)
                    )
                }

                itemsIndexed(items = albumSongs) { _, item ->
                    Row(
                        modifier = Modifier
                            .clickable { },
                    ) {
                        PixelyListItem(
                            headlineTextString = item.songTitle,
                            largeHeadline = false,
                            maxHeadlineLines = 1,
                            leadingContent = {
                                Text(
                                    text = item.track,
                                    modifier = Modifier
                                        .widthIn(max = 40.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip
                                )
                            },
                            trailingContent = {
                                val expandedItemMenu = remember { mutableStateOf(false) }

                                Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                                    IconButton(onClick = { expandedItemMenu.value = true }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
                                            contentDescription = stringResource(id = R.string.str_moreOptions)
                                        )
                                    }

                                    ItemDropDownMenu(
                                        expanded = expandedItemMenu,
                                        navController = navController
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}