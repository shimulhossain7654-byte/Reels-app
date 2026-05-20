package com.example.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.components.VideoPlayer
import com.example.models.sampleVideos

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen() {
    val pagerState = rememberPagerState(pageCount = { sampleVideos.size })

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        beyondViewportPageCount = 1
    ) { page ->
        val videoItem = sampleVideos[page]
        VideoPlayer(
            videoItem = videoItem,
            isCurrentlyPlaying = pagerState.currentPage == page
        )
    }
}
