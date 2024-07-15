package com.example.aqufishnewui20.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.aqufishnewui20.viewModels.MainViewModel

import com.example.aqufishnewui20.viewModels.sendRequest2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun feederLive(url: String, context: Context){

    if (isNetworkAvailable(context)) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Text("No network connection available", textAlign = TextAlign.Center)
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        networkInfo != null && networkInfo.isConnected
    }
}



@Composable
fun MonitoringScreen(viewModel: MainViewModel = viewModel(), ipaddress: String?, navController: NavHostController) {

    var isMotorOn by remember { mutableStateOf(false) }

    var motorSpeed by remember { mutableStateOf(0) }

    //val state = viewModel.state.collectAsState().value

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            val context = LocalContext.current
            Text(text = "Live Monitoring")
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .size(320.dp, 240.dp),
                elevation = CardDefaults.cardElevation(16.dp),
            ) {
                feederLive(url = "http://${ipaddress}:81/stream", context = context)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = {
                        isMotorOn = true
                        sendRequest2("http://192.168.100.89/motor/speed?value=$motorSpeed")
                    },
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(text = "Turn On Motor")
                }

                Button(
                    onClick = {
                        isMotorOn = false
                        sendRequest2("http://192.168.100.89/motor/off")
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
                Text(text = "IP Address: ${ipaddress}")

                Text(text = "Fish Feed Dispensed: 50 kg")
                Text(text = "Fish Weight: 100 g each on average")
            }

            ElevatedCard()

            Spacer(modifier = Modifier.height(8.dp))

            ScheduleCard()
        }
    }

}



@Composable
fun ScheduleCard() {

    var scheduleList by remember { mutableStateOf(listOf("8:00am", "12:00pm", "5:00pm")) }
    val newTime = remember { mutableStateOf(TextFieldValue("")) }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Schedule",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = { /* Add action */ }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Text(
                text = "Time",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            listOf("8:00am", "12:00pm", "5:00pm").forEach { time ->
                ScheduleItem(time)
            }

            TextButton(
                onClick = { /* Edit action */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Edit",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
fun ScheduleItem(time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = time,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        IconButton(onClick = { /* Delete action */ }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
            )
        }
    }
}

@Composable
fun ScrollableContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Add your static content here
        Text(
            text = "Scrollable Content",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        for (i in 1..50) {
            Text(
                text = "Item $i",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ElevatedCard() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(16.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Feed Conversion\nRatio",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = "1 : 1.7",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun FishFarmApp(navController: NavHostController, ipaddress: String?) {

    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
       // MonitoringScreen(ipaddress)
    }
}
