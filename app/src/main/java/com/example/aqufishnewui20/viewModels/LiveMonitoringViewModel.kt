package com.example.aqufishnewui20.viewModels

import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.telecom.Call
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import okhttp3.Response
import okhttp3.Callback
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LiveMonitoringViewModel: ViewModel() {
    private val _state = MutableStateFlow(LiveMonitoringState())
    val state: StateFlow<LiveMonitoringState> = _state



    fun updateIpAddress(ipAddress: String) {
        _state.value = _state.value.copy(ipAddress = ipAddress)
    }

    fun updateFishFeedDispensed(feedDispensed: Double) {
        _state.value = _state.value.copy(fishFeedDispensed = feedDispensed)
    }

    fun updateFishWeight(weight: Double) {
        _state.value = _state.value.copy(fishWeight = weight)
    }

    fun loadInitialData() {
        viewModelScope.launch {
            // Simulate loading initial data
            _state.value = LiveMonitoringState(
                ipAddress = "192.168.1.1",
                fishFeedDispensed = 10.5,
                fishWeight = 250.0
            )
        }
    }
}

data class LiveMonitoringState(
    val ipAddress: String = "",
    val fishFeedDispensed: Double = 0.0,
    val fishWeight: Double = 0.0
)

private val client = OkHttpClient()

fun sendRequest2(url: String) {
    CoroutineScope(Dispatchers.IO).launch {
        val request = Request.Builder()
            .url(url)
            .build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            // Handle the response if needed
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun sendRequest(url: String) {

    Log.d(TAG, "Sending HTTP request to URL: $url")

    val client = OkHttpClient()

    val request = Request.Builder()
        .url(url)
        .build()

    client.newCall(request).enqueue(object : Callback {

        override fun onFailure(call: okhttp3.Call, e: IOException) {
            Log.e(TAG, "Failed to send HTTP request: ${e.message}", e)
            TODO("Not yet implemented")
        }

        override fun onResponse(call: okhttp3.Call, response: Response) {
            Log.d(TAG, "HTTP request successful")
            TODO("Not yet implemented")
        }
    })
}

class ScheduleService : Service() {
    private val handler = Handler()
    private val interval: Long = 60000 // 1 minute interval
    private lateinit var scheduleList: List<String>

    override fun onCreate() {
        super.onCreate()
        // Load your scheduleList from a persistent storage or pass it through an Intent
        scheduleList = listOf("8:00am", "12:00pm", "5:00pm")
        handler.post(checkScheduleRunnable)
    }

    private val checkScheduleRunnable = object : Runnable {
        override fun run() {
            checkSchedule()
            handler.postDelayed(this, interval)
        }
    }

    private fun checkSchedule() {
        val currentTime = SimpleDateFormat("h:mma", Locale.getDefault()).format(Date())
        if (scheduleList.contains(currentTime)) {
            turnOnMotor()
        }
    }

    private fun turnOnMotor() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(checkScheduleRunnable)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}