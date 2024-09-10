package com.example.aqufishnewui20.screens


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
//import androidx.media3.common.PlaybackException
import androidx.media3.common.util.UnstableApi
//import androidx.media3.common.PlaybackException
//import androidx.media3.common.Player
//import androidx.media3.common.util.UnstableApi
//import androidx.media3.common.MediaItem
//import androidx.media3.common.PlaybackException
//import androidx.media3.common.Player
//import androidx.media3.common.util.UnstableApi
//import androidx.media3.exoplayer.DefaultRenderersFactory
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.example.aqufishnewui20.viewModels.MainViewModel
import com.example.aqufishnewui20.viewModels.sendRequest2
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.play.integrity.internal.al
import com.google.firebase.storage.FirebaseStorage

//import com.google.android.exoplayer2.ExoPlayer


//@Composable
//fun RtspPlayer(context: Context, rtspUrl: String, modifier: Modifier = Modifier) {
//    //val context = LocalContext.current
//    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
//
//    // Set up the RTSP stream
//    LaunchedEffect(rtspUrl) {
//        Log.d("RtspPlayer", "Preparing to play RTSP stream: $rtspUrl")
//        val mediaItem = MediaItem.fromUri(Uri.parse(rtspUrl))
//        exoPlayer.setMediaItem(mediaItem)
//        exoPlayer.prepare()
//        exoPlayer.playWhenReady = true
//
//        exoPlayer.addListener(object : Player.Listener {
//            override fun onPlaybackStateChanged(state: Int) {
//                when (state) {
//                    Player.STATE_READY -> Log.d("RtspPlayer", "Player is ready")
//                    Player.STATE_ENDED -> Log.d("RtspPlayer", "Playback ended")
//                    Player.STATE_IDLE -> Log.d("RtspPlayer", "Player is idle")
//                    Player.STATE_BUFFERING -> Log.d("RtspPlayer", "Player is buffering")
//                }
//            }
//
//            override fun onPlayerError(error: PlaybackException) {
//                Log.e("RtspPlayer", "Player error occurred: ${error.message}", error)
//            }
//        })
//    }
//
//    // Release the player when the composable is disposed
//    DisposableEffect(Unit) {
//        onDispose {
//            Log.d("RtspPlayer", "Releasing ExoPlayer")
//            exoPlayer.release()
//        }
//    }
//
//    // Render the PlayerView
//    AndroidView(
//        factory = { context ->
//            PlayerView(context).apply {
//                player = exoPlayer
//            }
//        },
//        modifier = modifier
//    )
//}


//@OptIn(UnstableApi::class)
//fun createPlayer(context: Context): ExoPlayer {
//    // Create a data source factory for RTSP
//    val dataSourceFactory = DefaultHttpDataSource.Factory()
//
//    // Create a media source for the RTSP stream
//    val mediaSource: MediaSource = RtspMediaSource.Factory()
//        .createMediaSource(MediaItem.fromUri("rtsp://1.tcp.ap.ngrok.io:20002/mystream"))
//
//    // Create a load control with custom buffering settings
//    val loadControl: LoadControl = DefaultLoadControl.Builder()
//        .setMinBufferMs(5000)
//        .setMaxBufferMs(10000)
//        .setBufferDurationsMs(
//            minBufferMs = 5000,
//            maxBufferMs = 10000,
//            bufferForPlaybackMs = 1000,
//            bufferForPlaybackAfterRebufferMs = 5000
//        )
//        .createDefaultLoadControl()
//
//    // Create the ExoPlayer instance
//    val player = ExoPlayer.Builder(context)
//        .setLoadControl(loadControl)
//        .build()
//
//    // Set the media source to be played
//    player.setMediaSource(mediaSource)
//
//    // Prepare the player
//    player.prepare()
//
//    // Add a listener to log errors and handle playback events
//    player.addListener(object : Listener {
//        override fun onPlayerError(error: PlaybackException) {
//            Log.e("ExoPlayerError", "Playback error: ${error.message}")
//        }
//
//        override fun onPlaybackStateChanged(playbackState: Int) {
//            when (playbackState) {
//                Player.STATE_READY -> Log.d("ExoPlayer", "Player is ready.")
//                Player.STATE_ENDED -> Log.d("ExoPlayer", "Playback ended.")
//                Player.STATE_IDLE -> Log.d("ExoPlayer", "Player is idle.")
//                Player.STATE_BUFFERING -> Log.d("ExoPlayer", "Player is buffering.")
//            }
//        }
//    })
//
//    return player
//}


//@OptIn(UnstableApi::class)
//fun createCustomLoadControl(): LoadControl {
//    return DefaultLoadControl.Builder()
//        .setBufferDurationsMs(
//            5000, // Minimum buffer size before playback starts
//            10000, // Maximum buffer size
//            1500, // Buffer required to start playback
//            1000 // Buffer required after rebuffering
//        )
//        .setTargetBufferBytes(-1) // Set to -1 to use default
//        .build()
//}
//
//@OptIn(UnstableApi::class)
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun LiveVideoPlayerExoPlayer(videoUrl: String?) {
//    val context = LocalContext.current
//
//    // Create a custom RenderersFactory that forces software decoding
//    val renderersFactory = DefaultRenderersFactory(context)
//        .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF)
//        //.setForceDisableMediaCodecHwDecoder(true) // Force software decoding
//
//    val loadControl = createCustomLoadControl()
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context)
//            .setLoadControl(loadControl)
//            .build()
//            .apply {
//                addListener(object : Player.Listener {
//                    override fun onPlaybackStateChanged(playbackState: Int) {
//                        when (playbackState) {
//                            Player.STATE_IDLE -> Log.d("LiveVideoPlayerExoPlayer", "Playback state: Idle")
//                            Player.STATE_BUFFERING -> Log.d("LiveVideoPlayerExoPlayer", "Playback state: Buffering")
//                            Player.STATE_READY -> Log.d("LiveVideoPlayerExoPlayer", "Playback state: Ready")
//                            Player.STATE_ENDED -> Log.d("LiveVideoPlayerExoPlayer", "Playback state: Ended")
//                        }
//                    }
//
//                    override fun onPlayerError(error: com.google.android.exoplayer2.PlaybackException) {
//                        Log.e("LiveVideoPlayerExoPlayer", "Playback encountered an error: ${error.message}")
//                        Log.e("LiveVideoPlayerExoPlayer", "Error details: ${error.cause?.message}")
//                    }
//                })
//            }
//    }
//
//    val playerState = rememberUpdatedState(exoPlayer)
//
//    LaunchedEffect(videoUrl) {
//        videoUrl?.let {
//            Log.d("LiveVideoPlayerExoPlayer", "Setting media item: $it")
//            try {
//                playerState.value.setMediaItem(MediaItem.fromUri(it))
//                playerState.value.prepare()
//                playerState.value.playWhenReady = true
//            } catch (e: Exception) {
//                Log.e("LiveVideoPlayerExoPlayer", "Error setting media item: ${e.message}")
//            }
//        } ?: run {
//            Log.w("LiveVideoPlayerExoPlayer", "No video URL available")
//        }
//    }
//
//
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { ctx ->
//            PlayerView(ctx).apply {
//                player = playerState.value
//                resizeMode = com.google.android.exoplayer2.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
//                Log.d("LiveVideoPlayerExoPlayer", "PlayerView initialized")
//            }
//        }
//    )
//
//    DisposableEffect(Unit) {
//        onDispose {
//            Log.d("LiveVideoPlayerExoPlayer", "Releasing player resources")
//            playerState.value.release()
//        }
//    }
//}
//
//
//
//@OptIn(UnstableApi::class)
//@Composable
//fun ExoPlayerView(rtspUrl: String) {
//    val context = LocalContext.current
//
//    // Create ExoPlayer instance
//    val player = remember {
//        ExoPlayer.Builder(context)
//            .setLoadControl(DefaultLoadControl())
//            .build().apply {
//                setMediaItem(MediaItem.fromUri(rtspUrl))
//                prepare()
//                playWhenReady = true
//                Log.d("ExoPlayerView", "Player created and prepared with URL: $rtspUrl")
//            }
//    }
//
//    // Log player state changes and errors
//    LaunchedEffect(player) {
//        player.addListener(object : Player.Listener {
//            override fun onPlaybackStateChanged(state: Int) {
//                Log.d("ExoPlayerView", "Playback state changed: $state")
//                // 1: PLAYING, 2: PAUSED, 3: BUFFERING, etc.
//            }
//
//            override fun onPlayerError(error: PlaybackException) {
//                Log.e("ExoPlayerView", "Player error: ${error.message}", error)
//                // Logging specific details about the exception
//                error.cause?.let { cause ->
//                    Log.e("ExoPlayerView", "Error cause: ${cause.message}", cause)
//                }
//            }
//
//            override fun onIsPlayingChanged(isPlaying: Boolean) {
//                Log.d("ExoPlayerView", "Playback isPlaying: $isPlaying")
//            }
//        })
//    }
//
//    // Release ExoPlayer when the composable is disposed
//    DisposableEffect(context) {
//        onDispose {
//            Log.d("ExoPlayerView", "Releasing ExoPlayer")
//            player.release()
//        }
//    }
//
//    // Use AndroidView to display the ExoPlayer content
//    AndroidView(
//        factory = { ctx ->
//            PlayerView(ctx).apply {
//                this.player = player
//                useController = true // Show playback controls
//                Log.d("ExoPlayerView", "PlayerView created")
//            }
//        },
//        modifier = Modifier.fillMaxSize()
//    )
//}
//
//@OptIn(UnstableApi::class)
//@Composable
//fun ExoPlayerView2(rtspUri: String) {
//    val context = LocalContext.current
//
//    val mediaSource = remember {
//        RtspMediaSource.Factory()
//            .setForceUseRtpTcp(true)
//            .setTimeoutMs(4000)
//            .createMediaSource(MediaItem.fromUri(rtspUri))
//    }
//
//    val player = remember {
//        ExoPlayer.Builder(context)
//            .build().apply {
//                setMediaSource(mediaSource)
//                prepare()
//                playWhenReady = true
//                Log.d("ExoPlayerView", "Player created with RtspMediaSource and prepared with RTSP URI: $rtspUri")
//            }
//    }
//
//    LaunchedEffect(player) {
//        player.addListener(object : Player.Listener {
//            override fun onPlaybackStateChanged(state: Int) {
//                Log.d("ExoPlayerView", "Playback state changed: $state")
//                if (state == Player.STATE_BUFFERING) {
//                    Log.d("ExoPlayerView", "Buffering...")
//                }
//            }
//
//            override fun onPlayerError(error: PlaybackException) {
//                Log.e("ExoPlayerView", "Player error: ${error.message}", error)
//                error.cause?.let { cause ->
//                    Log.e("ExoPlayerView", "Error cause: ${cause.message}", cause)
//                }
//            }
//        })
//    }
//
//    DisposableEffect(context) {
//        onDispose {
//            Log.d("ExoPlayerView", "Releasing ExoPlayer")
//            player.release()
//        }
//    }
//
//    Box {
//        AndroidView(
//            factory = { ctx ->
//                PlayerView(ctx).apply {
//                    this.player = player
//                    useController = true
//                    Log.d("ExoPlayerView", "PlayerView created")
//                }
//            },
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}

//@Composable
//fun IjkPlayerView(context: Context, rtspUrl: String) {
//    val surfaceView = remember {
//        SurfaceView(context).apply {
//            holder.addCallback(object : SurfaceHolder.Callback {
//                override fun surfaceCreated(holder: SurfaceHolder) {
//                    // Initialize media player
//                    val mediaPlayer = IjkMediaPlayer().apply {
//                        setDataSource(rtspUrl)
//                        setDisplay(holder)
//                        prepareAsync()
//                        setOnPreparedListener {
//                            it.start()
//                        }
//                    }
//                }
//
//                override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//                    // Handle surface changes if necessary
//                }
//
//                override fun surfaceDestroyed(holder: SurfaceHolder) {
//                    // Release media player when surface is destroyed
//                    (mediaPlayer as? IjkMediaPlayer)?.release()
//                }
//            })
//        }
//    }
//
//    LaunchedEffect(rtspUrl) {
//        // Handle any side effects if needed when URL changes
//    }
//
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { surfaceView }
//    )
//}

//private const val TAG = "ExoPlayerView"
//
//@OptIn(UnstableApi::class)
//@Composable
//fun ExoPlayerView(context: Context, rtmpUrl: String) {
//    // Initialize ExoPlayer
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context)
//            .setMediaSourceFactory(DefaultMediaSourceFactory(context))
//            .build().apply {
//                // Set up the MediaItem with RTMP URL and live configuration
//                val mediaItem = MediaItem.Builder()
//                    .setUri(rtmpUrl)
//                    .build()
//
//                // Use RtmpMediaSource if available
//                val mediaSource: MediaSource = RtmpMediaSource.Factory().createMediaSource(mediaItem)
//                setMediaSource(mediaSource)
//
//                // Add a listener to log playback state changes and errors
//                addListener(object : Player.Listener {
//                    override fun onPlayerError(error: PlaybackException) {
//                        Log.e("ExoPlayerView", "Player Error: ${error.message}", error)
//                    }
//
//                    override fun onPlaybackStateChanged(state: Int) {
//                        Log.d("ExoPlayerView", "Playback State Changed: $state")
//                    }
//                })
//
//                // Prepare and start playback
//                prepare()
//                playWhenReady = true
//            }
//    }
//
//    // Clean up resources when the Composable leaves the composition
//    DisposableEffect(Unit) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//
//    // Display the player view
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = { context ->
//            PlayerView(context).apply {
//                player = exoPlayer
//                Log.d("ExoPlayerView", "PlayerView created and assigned to ExoPlayer")
//            }
//        }
//    )
//}



//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun LiveVideoPlayer(viewModel: MainViewModel) {
//    val context = LocalContext.current
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            addListener(object : Player.Listener {
//                override fun onPlaybackStateChanged(playbackState: Int) {
//                    if (playbackState == Player.STATE_ENDED) {
//
//                    }
//                }
//            })
//        }
//    }
//
//    val videoUrl by viewModel.videoUrl.collectAsState()
//
//    // Update the video URL and play the video when it changes
//    LaunchedEffect(videoUrl) {
//        videoUrl?.let {
//            val mediaItem = MediaItem.fromUri(it)
//            exoPlayer.setMediaItem(mediaItem)
//            exoPlayer.prepare()
//            exoPlayer.play()
//        } ?: run {
//            // Optionally handle the case where no video URL is available
//            Toast.makeText(context, "No video available", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    // Display the video using ExoPlayer
//    AndroidView(factory = { ctx ->
//        PlayerView(ctx).apply {
//            player = exoPlayer
//        }
//    })
//
//    // Release ExoPlayer when composable is disposed
//    DisposableEffect(exoPlayer) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//}





@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonitoringScreen(context: Context, viewModel: MainViewModel, navController: NavHostController, ipAddress: String?) {

    var isMotorOn by remember { mutableStateOf(false) }

    var motorSpeed by remember { mutableStateOf(0) }

    //val context = LocalContext.current
    val storageReference = FirebaseStorage.getInstance().reference

    // Reference to the videos in Firebase Storage
    val video1Ref = storageReference.child("AqufishTestVideos/WizardOfOz1.mp4")
    val video2Ref = storageReference.child("AqufishTestVideos/WizardOfOz2.mp4")

    // Mutable state to hold the video URLs
    var videoUrls by remember { mutableStateOf<List<String>>(emptyList()) }

    // Fetch the video URLs from Firebase Storage
    LaunchedEffect(Unit) {
        video1Ref.downloadUrl.addOnSuccessListener { uri1 ->
            video2Ref.downloadUrl.addOnSuccessListener { uri2 ->
                videoUrls = listOf(uri1.toString(), uri2.toString())
            }
        }
    }

    // Play the videos if URLs are available


    Column (
        modifier = Modifier
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){

        //val context = LocalContext.current
        Text(text = "Live Monitoring")
        Spacer(modifier = Modifier.height(12.dp))
//        Card(
//            modifier = Modifier
//                .padding(16.dp)
//                .size(320.dp, 240.dp),
//            elevation = CardDefaults.cardElevation(16.dp),
//        ) {
//        }
        val ipAdd = "rtsp://$ipAddress/mystream"

        LiveVideoPlayerExoPlayer2(ipAdd)

        //WebViewLiveVideoPlayer(navController)
//        if (videoUrls.isNotEmpty()) {
//
//        } else {
//            // Show a loading or placeholder view while the video URLs are being fetched
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(240.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator()
//            }
//        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Button(
                onClick = {
                    isMotorOn = true
                    sendRequest2("http://10.17.220.168/motor/speed?value=$motorSpeed")
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Turn On Motor")
            }

            Button(
                onClick = {
                    isMotorOn = false
                    sendRequest2("http://10.17.220.168/motor/off")
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Turn Off Motor")
            }
        }

        Slider(
            value = motorSpeed.toFloat(),
            onValueChange = {
                motorSpeed = it.toInt()
                //if(isMotorOn){
                //  sendRequest("http://192.168.1.14/motor/speed?value=$motorSpeed")
                //}
                //else{
                //   sendRequest("http://192.168.1.14/motor/off")
                //}
                //sendRequest("http://192.168.0.10/motor/speed?value=$motorSpeed")
            },
            valueRange = 0f..255f,
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = motorSpeed.toString())

        Text(
            text = "Motor is ${if (isMotorOn) "On" else "Off"}"
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "IP Address: $ipAddress")

            Text(text = "Fish Feed Dispensed: 50 kg")
            Text(text = "Fish Weight: 100 g each on average")
        }

        ElevatedCard()

        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
                //.wrapContentHeight()
                //.animateContentSize(),

            elevation = CardDefaults.cardElevation(16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
        ) {

            AlarmCard(context, viewModel)

        }


    }

}

@Composable
fun LiveVideoPlayerExoPlayer2(url: String) {
    val context = LocalContext.current

    Log.d("LiveVideoPlayerExoPlayer2", "Creating ExoPlayer instance")
    val exoPlayer = remember {
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                50000,  // Minimum buffer duration before starting playback (50 seconds)
                150000, // Maximum buffer duration during playback (150 seconds)
                2500,  // Buffer required before starting playback (15 seconds)
                5000   // Buffer required after rebuffering (10 seconds)
            )
            .setTargetBufferBytes(-1) // Set a custom target buffer size (60 MB)
            .setPrioritizeTimeOverSizeThresholds(true) // Prioritize time-based buffering
            //.setBackBuffer(60000, false) // Retain 60 seconds of back buffer
            .build()
        ExoPlayer.Builder(context).setLoadControl(loadControl).build().apply {
            val mediaItem = MediaItem.fromUri(url)
            Log.d("LiveVideoPlayerExoPlayer2", "Setting media item with RTSP URL: $url")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    Log.d("LiveVideoPlayerExoPlayer2", "Playback state changed: $state")
                }

                override fun onPlayerError(error: PlaybackException) {
                    Log.e("LiveVideoPlayerExoPlayer2", "Player error: ${error.message}", error)
                }
            })
        }
    }

    DisposableEffect(url) {
        Log.d("LiveVideoPlayerExoPlayer2", "Disposing ExoPlayer")
        onDispose {
            exoPlayer.release()
            Log.d("LiveVideoPlayerExoPlayer2", "ExoPlayer released")
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                Log.d("LiveVideoPlayerExoPlayer2", "Setting player to PlayerView")
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    )
}

@Composable
fun MockVideoPlayer(videoUrls: List<String>) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaSources = videoUrls.map { url ->
                MediaItem.fromUri(url)
            }

            val concatenatingMediaSource = ConcatenatingMediaSource().apply {
                mediaSources.forEach { mediaItem ->
                    addMediaSource(DefaultMediaSourceFactory(context).createMediaSource(mediaItem))
                }
            }

            setMediaSource(concatenatingMediaSource)
            prepare()
            playWhenReady = true
            repeatMode = Player.REPEAT_MODE_ALL // Repeat all items
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    )
}



@Composable
fun ElevatedCard() {
    var showDialog by remember { mutableStateOf(false) }

    // Make the Card clickable
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { showDialog = true },
        elevation = CardDefaults.cardElevation(16.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Feed Conversion\nRatio",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "1 : 1.2",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

    // Display the AlertDialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Fish Information") },
            text = {
                Column {
                    Text("Average Fish Size: 25.7 cm")
                    Text("Average Fish Weight: 119.83 grams")
                    Text("Average Count of Fish: 178")

                    val foodWt = (1.2* 119.83 * 178 * 0.03)/(1000 * 3)
                    Text("Optimum Food: ${"%.2f".format(foodWt)} kg/meal")
                }
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text("OK")
                }
            }
        )
    }
}




@Composable
fun MjpegStreamWebView(url: String, modifier: Modifier = Modifier, onBack: () -> Unit) {
    var webViewState: Bundle? by remember { mutableStateOf(null) }
    var webView: WebView? = null

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                if (webViewState != null) {
                    restoreState(webViewState!!)
                } else {
                    loadUrl(url)
                }
                webView = this
            }
        },
        modifier = modifier
    )

    // Handle back press to either navigate back in the WebView history or exit the WebView
    BackHandler {
        webView?.let {
            if (it.canGoBack()) {
                it.goBack()
            } else {
                onBack() // Navigate out of the WebView screen
            }
        }
    }

    // Save the WebView state when the Composable is disposed
    // Ensure WebView is released when the Composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            webView?.destroy() // Destroy the WebView to release resources
        }
    }
}


@Composable
fun WebViewLiveVideoPlayer(navController: NavHostController) {
    MjpegStreamWebView(
        url = "http://192.168.47.14:81/stream",
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        onBack = {
            // Handle back navigation here, e.g., navigate to a different screen
            navController.popBackStack()
        }
    )
}
