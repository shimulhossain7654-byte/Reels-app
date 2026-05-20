package com.example.models

data class VideoItem(
    val id: String,
    val url: String,
    val username: String,
    val description: String,
    val musicTrack: String,
    val likes: Int,
    val comments: Int
)

val sampleVideos = listOf(
    VideoItem(
        id = "1",
        url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        username = "@creator_abby",
        description = "First time trying this filter! ✨ #magic",
        musicTrack = "Original Audio - abby_creates",
        likes = 12400,
        comments = 452
    ),
    VideoItem(
        id = "2",
        url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        username = "@cinematic_shots",
        description = "Dreamy vibes today... ☁️🎬",
        musicTrack = "Lofi Study Beats - chill",
        likes = 8520,
        comments = 112
    ),
    VideoItem(
        id = "3",
        url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
        username = "@fitness_pro",
        description = "Daily workout routine! Keep pushing 💪",
        musicTrack = "Workout Mix 2024",
        likes = 45600,
        comments = 1120
    ),
    VideoItem(
        id = "4",
        url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
        username = "@travel_diaries",
        description = "Into the wild 🌿🏕️",
        musicTrack = "Nature Sounds Vol.1",
        likes = 94300,
        comments = 3200
    )
)
