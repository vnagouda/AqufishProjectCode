package com.example.aqufishnewui20.screens


//import androidx.media3.common.PlaybackException
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


import android.content.Context
import android.net.Uri
import android.net.http.SslCertificate.restoreState
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.util.UnstableApi

import androidx.navigation.NavHostController
import com.example.aqufishnewui20.viewModels.MainViewModel
import com.example.aqufishnewui20.viewModels.sendRequest2
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView

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
fun MonitoringScreen(context: Context, viewModel: MainViewModel, navController: NavHostController?) {

    var isMotorOn by remember { mutableStateOf(false) }

    var motorSpeed by remember { mutableStateOf(0) }

    //val context = LocalContext.current


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
        val ipAdd = "http://13.213.74.217/hls/stream.m3u8"
        val ipAdd2 = "rtsp://1.tcp.ap.ngrok.io:20002/mystream"

        //HlsExoPlayer(ipAdd)
        LiveVideoPlayerExoPlayer3(ipAdd2)
//        try {
//            LiveVideoPlayerExoPlayer2(ipAdd)
//            Log.d("MonitoringScreen", "LiveVideoPlayerExoPlayer2 loaded successfully.")
//        } catch (e: Exception) {
//            Log.e("MonitoringScreen", "Error loading LiveVideoPlayerExoPlayer2: ${e.message}", e)
//        }

//        WebViewLiveVideoPlayer(navController)
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
            Text(text = "IP Address: ")

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

//@OptIn(UnstableApi::class)
//@Composable
//fun HlsExoPlayer(
//    streamUrl: String,
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    // State to track player loading
//    var isBuffering by remember { mutableStateOf(true) }
//
//    // Initialize ExoPlayer with logging
//    val exoPlayer = remember {
//        try {
//            val trackSelector = DefaultTrackSelector(context)
//            ExoPlayer.Builder(context)
//                .setTrackSelector(trackSelector)
//                .build().apply {
//                    val mediaItem = MediaItem.fromUri(Uri.parse(streamUrl))
//                    setMediaItem(mediaItem)
//                    prepare()
//                    playWhenReady = true
//
//                    Log.d("HlsExoPlayer", "ExoPlayer initialized and ready to play.")
//                }
//        } catch (e: Exception) {
//            Log.e("HlsExoPlayer", "Error initializing ExoPlayer: ${e.localizedMessage}")
//            null
//        }
//    }
//
//    // Handle lifecycle events for proper player release
//    DisposableEffect(lifecycleOwner) {
//        if (exoPlayer != null) {
//            val lifecycleObserver = LifecycleEventObserver { _, event ->
//                when (event) {
//                    Lifecycle.Event.ON_PAUSE -> {
//                        Log.d("HlsExoPlayer", "Pausing ExoPlayer.")
//                        exoPlayer.playWhenReady = false
//                        exoPlayer.pause()
//                    }
//                    Lifecycle.Event.ON_RESUME -> {
//                        Log.d("HlsExoPlayer", "Resuming ExoPlayer.")
//                        exoPlayer.playWhenReady = true
//                        exoPlayer.play()
//                    }
//                    Lifecycle.Event.ON_DESTROY -> {
//                        Log.d("HlsExoPlayer", "Releasing ExoPlayer.")
//                        exoPlayer.release()
//                    }
//                    else -> {}
//                }
//            }
//
//            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
//
//            // Return DisposableEffectResult to remove observer and release player
//            onDispose {
//                lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
//                exoPlayer.release()
//                Log.d("HlsExoPlayer", "ExoPlayer and LifecycleObserver released.")
//            }
//        } else {
//            onDispose {
//                Log.e("HlsExoPlayer", "ExoPlayer was null, nothing to release.")
//            }
//        }
//    }
//
//    // Layout for showing ExoPlayer or a loading indicator
//    Box(modifier = modifier, contentAlignment = Alignment.Center) {
//        exoPlayer?.let {
//            AndroidView(
//                factory = {
//                    PlayerView(context).apply {
//                        player = exoPlayer
//                        useController = true // Show playback controls
//                        setKeepContentOnPlayerReset(true)
//
//                        // Set the resize mode to maintain aspect ratio
//                        setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT)
//                    }
//                },
//                update = {
//                    exoPlayer.addListener(object : Player.Listener {
//                        override fun onIsLoadingChanged(isLoading: Boolean) {
//                            isBuffering = isLoading
//                        }
//
//                        override fun onPlayerError(error: PlaybackException) {
//                            Log.e("HlsExoPlayer", "Playback error: ${error.message}, Error code: ${error.errorCode}, Cause: ${error.cause}")
//                        }
//                    })
//                },
//                modifier = Modifier.wrapContentSize() // Ensure the video respects aspect ratio
//            )
//        }
//
//        // Show loading indicator while buffering
//        if (isBuffering) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//    }
//}



//@SuppressLint("SetJavaScriptEnabled")
//@Composable
//fun LiveMonitoringScreen() {
//    val TAG = "ExoPlayerLogging"
//
//    // Get the current context
//    val context = LocalContext.current
//
//    // Initialize the ExoPlayer
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            // Define the media source (HLS Stream)
//            val mediaItem = MediaItem.fromUri("http://13.213.74.217/hls/stream.m3u8")
//            setMediaItem(mediaItem)
//
//            // Prepare the player
//            prepare()
//
//            // Start playback when ready
//            playWhenReady = true
//
//            // Add a listener for logging events
//            addListener(object : Player.Listener {
//                override fun onPlaybackStateChanged(playbackState: Int) {
//                    when (playbackState) {
//                        Player.STATE_BUFFERING -> Log.d(TAG, "Player is buffering...")
//                        Player.STATE_READY -> Log.d(TAG, "Player is ready to play!")
//                        Player.STATE_ENDED -> Log.d(TAG, "Playback ended.")
//                        Player.STATE_IDLE -> Log.d(TAG, "Player is idle.")
//                    }
//                }
//
//                override fun onPlayerError(error: PlaybackException) {
//                    Log.e(TAG, "Playback error: ${error.message}")
//                }
//
//                override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
//                    Log.d(TAG, "Play when ready: $playWhenReady, reason: $reason")
//                }
//            })
//        }
//    }
//
//    // Clean up ExoPlayer resources when the Composable is destroyed
//    DisposableEffect(
//        AndroidView(
//            modifier = Modifier
//                .fillMaxSize(), // Fill screen size
//            factory = {
//                PlayerView(context).apply {
//                    player = exoPlayer
//                    useController = true // Enable media controls
//                }
//            }
//        )
//    ) {
//        onDispose {
//            Log.d(TAG, "Releasing ExoPlayer...")
//            exoPlayer.release() // Ensure player is released properly
//        }
//    }
//}
//
//private const val TAG = "LiveVideoPlayerWebView"
//
//@Composable
//fun LiveVideoPlayerWebView1(videoUrl: String, modifier: Modifier = Modifier) {
//    AndroidView(
//        modifier = modifier.fillMaxSize(),
//        factory = { context ->
//            WebView(context).apply {
//                // Enable JavaScript and media playback
//                settings.javaScriptEnabled = true
//                settings.mediaPlaybackRequiresUserGesture = false
//                settings.cacheMode = WebSettings.LOAD_NO_CACHE
//
//                // Configure WebViewClient to handle page loads within WebView
//                webViewClient = object : WebViewClient() {
//                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                        super.onPageStarted(view, url, favicon)
//                        Log.d(TAG, "Page started loading: $url")
//                    }
//
//                    override fun onPageFinished(view: WebView?, url: String?) {
//                        super.onPageFinished(view, url)
//                        Log.d(TAG, "Page finished loading: $url")
//                    }
//
//                    override fun onReceivedError(
//                        view: WebView?,
//                        request: android.webkit.WebResourceRequest?,
//                        error: WebResourceError?
//                    ) {
//                        super.onReceivedError(view, request, error)
//                        Log.e(TAG, "Error loading page: ${error?.description}")
//                    }
//                }
//
//                // Configure WebChromeClient for handling media (video player UI)
//                webChromeClient = object : WebChromeClient() {
//                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
//                        super.onProgressChanged(view, newProgress)
//                        Log.d(TAG, "Loading progress: $newProgress%")
//                    }
//                }
//
//                // Load the video URL
//                loadUrl(videoUrl)
//            }
//        }
//    )
//}
//
//@Composable
//fun LiveVideoPlayerWebView(url: String) {
//    val context = LocalContext.current
//    val webViewState = remember { mutableStateOf(false) }
//
//    AndroidView(
//        factory = {
//            WebView(context).apply {
//                settings.javaScriptEnabled = true
//                settings.mediaPlaybackRequiresUserGesture = false
//                webViewClient = WebViewClient()
//
//                // Check if the URL is valid
//                try {
//                    loadUrl(url)
//                    webViewState.value = true
//                } catch (e: Exception) {
//                    Log.e("LiveVideoPlayerWebView", "Error loading URL: $url", e)
//                    webViewState.value = false
//                }
//            }
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(240.dp)
//    )
//
//    if (!webViewState.value) {
//        // Show an error if the WebView fails to load the video
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text(
//                text = "Error loading video",
//                color = MaterialTheme.colorScheme.error,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//}
//
//@Composable
//fun LiveVideoPlayerExoPlayer4() {
//    val context = LocalContext.current
//    val url = "http://13.213.74.217/hls/stream.m3u8"
//    val isLoading = remember { mutableStateOf(true) }
//    val errorMessage = remember { mutableStateOf<String?>(null) }
//
//    Log.d("LiveVideoPlayerExoPlayer3", "Video URL: $url")
//
//    val exoPlayer = remember {
//        try {
//            val loadControl = DefaultLoadControl.Builder().build()
//            ExoPlayer.Builder(context).setLoadControl(loadControl).build().apply {
//                val mediaItem = MediaItem.fromUri(url)
//                setMediaItem(mediaItem)
//
//                Log.d("LiveVideoPlayerExoPlayer3", "Preparing ExoPlayer")
//                prepare()
//
//                playWhenReady = true
//
//                addListener(object : Player.Listener {
//                    override fun onPlaybackStateChanged(state: Int) {
//                        when (state) {
//                            Player.STATE_BUFFERING -> {
//                                Log.d("ExoPlayerState", "Buffering...")
//                                isLoading.value = true
//                                errorMessage.value = null
//                            }
//                            Player.STATE_READY -> {
//                                Log.d("ExoPlayerState", "Ready to play.")
//                                isLoading.value = false
//                                errorMessage.value = null
//                            }
//                            Player.STATE_ENDED -> {
//                                Log.d("ExoPlayerState", "Playback ended.")
//                                isLoading.value = false
//                            }
//                            Player.STATE_IDLE -> {
//                                Log.d("ExoPlayerState", "Player is idle.")
//                                isLoading.value = true
//                            }
//                        }
//                    }
//
//                    override fun onPlayerError(error: PlaybackException) {
//                        Log.e("ExoPlayerError", "Playback error: ${error.message}", error)
//                        isLoading.value = false
//                        errorMessage.value = error.message
//                    }
//                })
//            }
//        } catch (e: Exception) {
//            Log.e("LiveVideoPlayerExoPlayer3", "Error creating ExoPlayer", e)
//            null
//        }
//    }
//
//    DisposableEffect(url) {
//        onDispose {
//            Log.d("LiveVideoPlayerExoPlayer3", "Releasing ExoPlayer")
//            exoPlayer?.release()
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxWidth().height(240.dp)) {
//        if (errorMessage.value != null) {
//            Log.e("LiveVideoPlayerExoPlayer3", "Displaying error: ${errorMessage.value}")
//            Text(
//                text = "Error: ${errorMessage.value}",
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.align(Alignment.Center),
//                textAlign = TextAlign.Center
//            )
//        } else {
//            AndroidView(
//                factory = {
//                    PlayerView(context).apply {
//                        Log.d("LiveVideoPlayerExoPlayer3", "Setting up PlayerView")
//                        player = exoPlayer
//                        layoutParams = FrameLayout.LayoutParams(
//                            FrameLayout.LayoutParams.MATCH_PARENT,
//                            FrameLayout.LayoutParams.MATCH_PARENT
//                        )
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(240.dp)
//            )
//
//            if (isLoading.value) {
//                Log.d("LiveVideoPlayerExoPlayer3", "Displaying loading indicator")
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center),
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
//    }
//}
//
//
//@Composable
//fun LiveVideoPlayerExoPlayer3(url: String) {
//    val context = LocalContext.current
//    val isLoading = remember { mutableStateOf(true) }
//    val errorMessage = remember { mutableStateOf<String?>(null) }
//
//    // Logging URL to ensure correct value is passed
//    Log.d("LiveVideoPlayerExoPlayer3", "Video URL: $url")
//
//    // Create ExoPlayer instance
//    val exoPlayer = remember {
//        val loadControl = DefaultLoadControl.Builder().build()
//        ExoPlayer.Builder(context).setLoadControl(loadControl).build().apply {
//            val mediaItem = MediaItem.fromUri(url)
//            setMediaItem(mediaItem)
//            prepare()
//            playWhenReady = true
//
//            addListener(object : Player.Listener {
//                override fun onPlaybackStateChanged(state: Int) {
//                    when (state) {
//                        Player.STATE_BUFFERING -> {
//                            Log.d("ExoPlayerState", "Buffering...")
//                            isLoading.value = true
//                            errorMessage.value = null // Clear error when loading
//                        }
//                        Player.STATE_READY -> {
//                            Log.d("ExoPlayerState", "Ready to play.")
//                            isLoading.value = false
//                            errorMessage.value = null
//                        }
//                        Player.STATE_ENDED -> {
//                            Log.d("ExoPlayerState", "Playback ended.")
//                            isLoading.value = false
//                        }
//                        Player.STATE_IDLE -> {
//                            Log.d("ExoPlayerState", "Player is idle.")
//                            isLoading.value = true
//                        }
//                        else -> {
//                            Log.d("ExoPlayerState", "Unknown state: $state")
//                        }
//                    }
//                }
//
//                override fun onPlayerError(error: PlaybackException) {
//                    Log.e("ExoPlayerError", "Playback error: ${error.message}", error)
//                    isLoading.value = false
//                    errorMessage.value = error.message
//                }
//            })
//        }
//    }
//
//    DisposableEffect(url) {
//        onDispose {
//            Log.d("LiveVideoPlayerExoPlayer3", "Releasing ExoPlayer")
//            exoPlayer.release()
//        }
//    }
//
//    // Display the video player or the error/loading state
//    Box(modifier = Modifier.fillMaxWidth().height(240.dp)) {
//        if (errorMessage.value != null) {
//            Log.e("LiveVideoPlayerExoPlayer3", "Displaying error: ${errorMessage.value}")
//            Text(
//                text = "Error: ${errorMessage.value}",
//                color = MaterialTheme.colorScheme.error,
//                modifier = Modifier.align(Alignment.Center),
//                textAlign = TextAlign.Center
//            )
//        } else {
//            AndroidView(
//                factory = {
//                    PlayerView(context).apply {
//                        Log.d("LiveVideoPlayerExoPlayer3", "Setting up PlayerView")
//                        player = exoPlayer
//                        layoutParams = FrameLayout.LayoutParams(
//                            FrameLayout.LayoutParams.MATCH_PARENT,
//                            FrameLayout.LayoutParams.MATCH_PARENT
//                        )
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(240.dp)
//            )
//
//            // Show CircularProgressIndicator while video is buffering
//            if (isLoading.value) {
//                Log.d("LiveVideoPlayerExoPlayer3", "Displaying loading indicator")
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center),
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
//    }
//}
//
//


@OptIn(UnstableApi::class)
@Composable
fun LiveVideoPlayerExoPlayer2(url: String) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    // Log video URL before setting up ExoPlayer
    Log.d("LiveVideoPlayerExoPlayer2", "RTSP URL received: $url")

    val exoPlayer = remember {
        try {
            val loadControl = DefaultLoadControl.Builder().build()
            ExoPlayer.Builder(context).setLoadControl(loadControl).build().apply {
                val mediaItem = MediaItem.fromUri(url)
                Log.d("LiveVideoPlayerExoPlayer2", "Setting media item with URL: $url")
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true

                // Add a listener to track the playback state and errors
                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        when (state) {
                            Player.STATE_BUFFERING -> {
                                Log.d("LiveVideoPlayerExoPlayer2", "Buffering...")
                                isLoading.value = true
                            }
                            Player.STATE_READY -> {
                                Log.d("LiveVideoPlayerExoPlayer2", "Ready to play.")
                                isLoading.value = false
                                errorMessage.value = null
                            }
                            Player.STATE_ENDED -> {
                                Log.d("LiveVideoPlayerExoPlayer2", "Playback ended.")
                                isLoading.value = false
                            }
                            Player.STATE_IDLE -> {
                                Log.d("LiveVideoPlayerExoPlayer2", "Player is idle.")
                                isLoading.value = true
                            }
                        }
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        Log.e("LiveVideoPlayerExoPlayer2", "Player error: ${error.message}", error)
                        errorMessage.value = error.message
                        isLoading.value = false
                    }
                })
            }
        } catch (e: Exception) {
            Log.e("LiveVideoPlayerExoPlayer2", "Error creating ExoPlayer: ${e.message}", e)
            null
        }
    }

    DisposableEffect(url) {
        Log.d("LiveVideoPlayerExoPlayer2", "Disposing ExoPlayer")
        onDispose {
            exoPlayer?.release()
            Log.d("LiveVideoPlayerExoPlayer2", "ExoPlayer released")
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(240.dp)) {
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (errorMessage.value != null) {
            Text(
                text = "Error: ${errorMessage.value}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        } else {
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
    }
}
//
//@Composable
//fun HlsStreamPlayer(url: String) {
//    val context = LocalContext.current
//
//    // Remember the ExoPlayer instance and set it up with HLS media
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            try {
//                val mediaItem = MediaItem.Builder()
//                    .setUri(Uri.parse(url))
//                    .setMimeType(MimeTypes.APPLICATION_M3U8) // Explicitly set the MIME type for HLS
//                    .build()
//
//                setMediaItem(mediaItem)
//                prepare()
//                playWhenReady = true // Autoplay when ready
//            } catch (e: Exception) {
//                Log.e("HlsStreamPlayer", "ExoPlayer initialization error: ${e.message}")
//                e.printStackTrace()
//            }
//        }
//    }
//
//    // Ensure that ExoPlayer resources are released when no longer needed
//    DisposableEffect(key1 = exoPlayer) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//
//    // Use AndroidView to create a PlayerView that links to the ExoPlayer instance
//    AndroidView(
//        factory = {
//            PlayerView(context).apply {
//                player = exoPlayer
//                useController = true // Enable playback controls
//                layoutParams = FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//            }
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(240.dp) // Set the height as needed
//    )
//}
//
//
//@Composable
//fun MockVideoPlayer(videoUrls: List<String>) {
//    val context = LocalContext.current
//
//    val exoPlayer = remember {
//        ExoPlayer.Builder(context).build().apply {
//            val mediaSources = videoUrls.map { url ->
//                MediaItem.fromUri(url)
//            }
//
//            val concatenatingMediaSource = ConcatenatingMediaSource().apply {
//                mediaSources.forEach { mediaItem ->
//                    addMediaSource(DefaultMediaSourceFactory(context).createMediaSource(mediaItem))
//                }
//            }
//
//            setMediaSource(concatenatingMediaSource)
//            prepare()
//            playWhenReady = true
//            repeatMode = Player.REPEAT_MODE_ALL // Repeat all items
//        }
//    }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            exoPlayer.release()
//        }
//    }
//
//    AndroidView(
//        factory = {
//            PlayerView(context).apply {
//                player = exoPlayer
//                useController = false
//                layoutParams = FrameLayout.LayoutParams(
//                    FrameLayout.LayoutParams.MATCH_PARENT,
//                    FrameLayout.LayoutParams.MATCH_PARENT
//                )
//            }
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(240.dp)
//    )
//}



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
        url = "http://13.213.74.217/hls/stream",
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        onBack = {
            // Handle back navigation here, e.g., navigate to a different screen
            navController.popBackStack()
        }
    )
}

@Composable
fun LiveVideoPlayerExoPlayer3(url: String) {
    val context = LocalContext.current
    Log.d("LiveVideoPlayerExoPlayer2", "Composable called")

    val exoPlayer = remember(url) {
        Log.d("LiveVideoPlayerExoPlayer2", "Creating ExoPlayer instance")
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(50000, 150000, 2500, 5000)
            .setTargetBufferBytes(-1)
            .setPrioritizeTimeOverSizeThresholds(true)
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
        onDispose {
            Log.d("LiveVideoPlayerExoPlayer2", "Disposing ExoPlayer")
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

