package com.example.aqufishnewui20.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.aqufishnewui20.R
import com.example.aqufishnewui20.viewModels.DashboardViewModel
import com.example.aqufishnewui20.viewModels.LoginViewModel
import com.example.aqufishnewui20.viewModels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

//import com.example.aqufishnewui20.viewModels.DashboardViewModel





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBottomBar2(){


    Column(
        modifier = Modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
        UserAndFeederDetails2()
        FeederOptions2()
    }

}


@Composable
fun UserAndFeederDetails2() {

    //val feederInfo = viewModel.feederInfo.value


    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
        ) {

            Column(
                Modifier.fillMaxWidth(0.7f)
            ) {
                Text(text = "Selamat Datang ", fontSize = 25.sp)
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

@Composable
fun CardDetails2( duration: Int, dailyFeedWeight: Float, totalFeedWeight: Float){


    Card(
        modifier = Modifier
            .width(300.dp)
            .height(150.dp) // Increased height
            .padding(8.dp)
            .clickable {

            },
        elevation = CardDefaults.cardElevation(16.dp)
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
                    Text(text = "AquFeeder")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Daily feed(kg): ${dailyFeedWeight}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Total feed(kg): ${totalFeedWeight}")
                }

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                ) {
                    CircularProgressIndicator(
                        progress = duration / 60f, // Assuming feedDuration is out of 60 minutes
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Blue,
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


@Composable
fun FeederOptions2(){

    val scrollState = rememberScrollState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(text = "AquFeeder")
    }
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(scrollState),
    ){
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(5) { ipAddress ->
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
                    CardDetails2(duration = 60, dailyFeedWeight = 100f, totalFeedWeight = 200f)
                }
            }
        }
    }
}