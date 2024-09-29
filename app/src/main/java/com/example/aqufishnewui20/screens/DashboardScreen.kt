package com.example.aqufishnewui20.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.aqufishnewui20.R
import com.example.aqufishnewui20.viewModels.MainViewModel

//import com.example.aqufishnewui20.viewModels.DashboardViewModel



@Composable
fun MainPageDashboard(viewModel : MainViewModel, navController: NavHostController){
    TopBottomBar(viewModel = viewModel, navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBottomBar(viewModel : MainViewModel, navController: NavHostController){


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        UserAndFeederDetails(viewModel = viewModel)
        FeederOptions(viewModel = viewModel, navController = navController)
    }

}


@SuppressLint("NewApi")
@Composable
fun UserAndFeederDetails(viewModel: MainViewModel) {

    //val feederInfo = viewModel.feederInfo.value
    val userData by viewModel.userData.observeAsState()
    val userFeeders by viewModel.userFeeders.observeAsState()
    //val isLoading by viewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        val currentUser = viewModel.auth.currentUser
        if (currentUser != null) {
            Log.d("FeederOptions", "Fetching user data for UID: ${currentUser.uid}")
            viewModel.fetchUserData(currentUser.uid)
        }
    }

    userData?.let { user ->
        Log.d("UserAndFeederDetails", "User data observed: $user")

        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
            ) {

                Column(
                    Modifier.fillMaxWidth(0.7f)
                ) {
                    Text(text = "Selamat Datang ${user.name}", fontSize = 25.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Periska keadaan ikan anda pastikan anda tidak kelaparan!")
                }
                Box(
                    //Modifier.fillMaxWidth(1f)
                ){
                    Image(painter = painterResource(id = R.drawable.welcomeimage), contentDescription = "Image")
                }
            }

            Row (){
                Column(
                    Modifier.fillMaxWidth(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "Daily Feed(kg)")
                    Text(text = " kg")
                }

                Column(
                    Modifier.fillMaxWidth(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "Total Feed(kg)")
                    Text(text = " kg")
                }
            }
        }
    }

}

@Composable
fun CardDetails(feederName: String, ipAddress: String, navController: NavHostController, duration: Int, dailyFeedWeight: Float, location: String, count: Int){

    Card(
        modifier = Modifier
            .width(300.dp)
            .height(150.dp) // Increased height
            .padding(8.dp)
            .clickable {
                val encodedIpAddress = Uri.encode(ipAddress)
                Log.d("CardDetails", "Navigating to live_monitoring with IP: $encodedIpAddress")
                navController.navigate("live_monitoring/$encodedIpAddress")
            },
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly // Equally space items vertically
                ) {
                    Text(text = feederName, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Daily feed(kg): $dailyFeedWeight", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Location: $location", fontSize = 13.sp)
                }

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                ) {
                    CircularProgressIndicator(
                        progress = duration / 60f, // Assuming feedDuration is out of 60 minutes
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        strokeWidth = 8.dp
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "${duration}min", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}


@SuppressLint("NewApi")
@Composable
fun FeederOptions(
    navController: NavHostController,
    viewModel: MainViewModel
){

    val userData by viewModel.userData.observeAsState()
    val userFeeders by viewModel.userFeeders.observeAsState()
    //val isLoading by viewModel.isLoading.collectAsState()

    Log.d("FeederOptions", "Feeders data observed: $userFeeders")

    //val scrollState = rememberScrollState()

    userData?.let { user ->

        Card(
            modifier = Modifier
                //.fillMaxWidth()
                //.wrapContentSize()
                .height(50.dp)
                .padding(4.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "AquFeeder", fontSize = 20.sp, fontStyle = FontStyle.Italic)
            }
        }
        //Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .padding(8.dp)
                //.verticalScroll(scrollState),
        ){
            Spacer(modifier = Modifier.height(16.dp))

            var count = 0
            userFeeders?.forEach { feeder ->

                Log.d("FeederOptions", "Feeders data observed: $feeder")
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp, 150.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.newaqumonitorimage),
                            contentDescription = "Image"
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    CardDetails(
                        feederName = feeder.name,
                        ipAddress = feeder.ipAddress,
                        duration = 60,
                        dailyFeedWeight = 100f,
                        location = feeder.location,
                        navController = navController,
                        count = count
                    )
                    count++
                }
            }
        }
    }?: run {
        Log.e("FeederOptions", "No feeders data available")
    }
}