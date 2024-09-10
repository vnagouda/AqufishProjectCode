package com.example.aqufishnewui20.workers

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.aqufishnewui20.viewModels.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MotorWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val client = OkHttpClient()

    override fun doWork(): Result {
        val url = inputData.getString("url") ?: return Result.failure()

        Log.d("MotorWorker", "Executing work with URL: $url")


        return try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            Log.d("MotorWorker", "Response code: ${response.code}")

            if (!response.isSuccessful) {
                Log.e("MotorWorker", "Failed with code: ${response.code}")
                throw IOException("Unexpected code $response")
            }

            Log.d("MotorWorker", "Request successful")
            Result.success()
        } catch (e: Exception) {
            Log.e("MotorWorker", "Error executing request", e)
            Result.failure()
        }
    }
}
