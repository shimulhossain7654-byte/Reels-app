package com.example.components

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.models.VideoItem

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    videoItem: VideoItem,
    isCurrentlyPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    // Create ExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    // Effect to handle play/pause based on visibility and URL changes
    LaunchedEffect(videoItem.url, isCurrentlyPlaying) {
        if (isCurrentlyPlaying) {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoItem.url))
            if (exoPlayer.currentMediaItem != mediaItem) {
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
            }
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    // Cleanup when composed out
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
    
    var isMuted by remember { mutableStateOf(false) }
    
    Box(modifier = modifier.fillMaxSize().background(Color.Black)) {
        // Video View
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    // Tap to mute/unmute
                    isMuted = !isMuted
                    exoPlayer.volume = if (isMuted) 0f else 1f
                }
        )

        // Overlay Content
        VideoOverlay(videoItem = videoItem, modifier = Modifier.align(Alignment.BottomStart))
    }
}

@Composable
fun VideoOverlay(videoItem: VideoItem, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left Side: Info
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = videoItem.username,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = videoItem.description,
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = "Music",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = videoItem.musicTrack,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        // Right Side: Actions
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile image mock
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
            }
            Spacer(modifier = Modifier.height(24.dp))
            
            // Like
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Like",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Text(text = "${videoItem.likes}", color = Color.White, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(24.dp))
            
            // Comment
            Icon(
                imageVector = Icons.Default.ChatBubbleOutline,
                contentDescription = "Comment",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Text(text = "${videoItem.comments}", color = Color.White, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(24.dp))
            
            // Share
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Share",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Text(text = "Share", color = Color.White, fontSize = 12.sp)
        }
    }
}
