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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alessiocameroni.pixely_components.PixelyListItem
import com.alessiocameroni.pixely_components.PixelySectionTitle
import com.alessiocameroni.revomusicplayer.R
import com.alessiocameroni.revomusicplayer.data.classes.AlbumSongData
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumViewScreen(
    albumId: Long,
    navController: NavController,
    navControllerBottomBar: NavHostController,
    viewModel: AlbumViewViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberLazyListState()
    val textVisibility = remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }
    val albumSongs = viewModel.albumSongs

    val selectedSortType by remember { viewModel.sortingType }
    val selectedSortOrder by remember { viewModel.sortingOrder }

    listSort(
        albumSongs,
        selectedSortOrder,
        selectedSortType
    )

    LaunchedEffect(Unit) {
        viewModel.initializeAlbumSongsList(context, albumId)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AlbumViewTopActionBar(
                navController = navController,
                navControllerBottomBar = navControllerBottomBar,
                viewModel = viewModel,
                textVisibility = textVisibility,
                scrollBehavior = scrollBehavior,
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
                        navControllerBottomBar = navControllerBottomBar,
                        viewModel = viewModel,
                        leadingUnit = {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(viewModel.albumCoverUri.value)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = stringResource(id = R.string.str_albumImage),
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    )
                }

                item {
                    PixelySectionTitle(
                        stringTitle = stringResource(id = R.string.str_songs),
                        horizontalContentPadding = 15.dp
                    )
                }

                itemsIndexed(items = albumSongs) { _, item ->
                    Row(
                        modifier = Modifier
                            .clickable {  },
                    ) {
                        PixelyListItem(
                            headlineTextString = item.songTitle,
                            largeHeadline = false,
                            maxHeadlineLines = 1,
                            leadingContent = {
                                Text(
                                    text = if(item.track == 0) "-" else item.track.toString(),
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

                                        AlbumViewItemDropDownMenu(
                                            expanded = expandedItemMenu
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

private fun listSort(
    songs: MutableList<AlbumSongData>,
    sortOrder: Int,
    sortType: Int
) {
    when(sortOrder) {
        0 -> {
            when(sortType) {
                0 -> songs.sortBy { it.track }
                1 -> songs.sortBy { it.songTitle }
                2 -> songs.sortBy { it.duration }
            }
        }
        1 -> {
            when(sortType) {
                0 -> songs.sortByDescending { it.track }
                1 -> songs.sortByDescending { it.songTitle }
                2 -> songs.sortByDescending { it.duration }
            }
        }
    }
}