package com.alessiocameroni.revomusicplayer.data.classes

import android.net.Uri

data class SongEntity(
    var songId: Long,
    var contentUri: Uri,
    var songTitle: String,
    var artistId: Long,
    var artist: String,
    var albumId: Long,
    val album: String,
    val albumCoverUri: Uri,
    var duration: Int,
    val dateAdded: Long,
)