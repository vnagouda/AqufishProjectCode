package com.example.aqufishnewui20.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.aqufishnewui20.R
import com.example.aqufishnewui20.viewModels.LoginViewModel
import com.example.aqufishnewui20.viewModels.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(),
){

    BackHandler {
        // Do nothing on back press
    }


    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Surface(){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //Log.d(TAG, "LoginScreen: Composable initialized")

            Image(
                painter = painterResource(id = R.drawable.finallogo),
                contentDescription = "Login Page",
                modifier = Modifier.size(200.dp)
            )
            Text(text = "Welcome Back", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Login to your account")

            Spacer(modifier = Modifier.height(16.dp))
            when (loginSuccess) {
                true -> {

                    Text(
                        "Login successful",
                        color = MaterialTheme.colorScheme.primary
                    )
                    LaunchedEffect(Unit) {

                        delay(500)
                        navController.navigate("dashboard")
                    }
                }
                false -> Text("Login failed", color = MaterialTheme.colorScheme.error)
                null -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = email, onValueChange = { viewModel.onEmailChange(it) }, label = {
                Text(text = "Email Address")
            })

            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = password, onValueChange = { viewModel.onPasswordChange(it) }, label = {
                Text(text = "Password")
            }, visualTransformation = PasswordVisualTransformation())

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    //Log.d(TAG, "Login button clicked")
                    viewModel.login()

                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                if (isLoading){
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else{
                    Text(text = "Login")
                }


            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Forgot Password?")
            }

            Spacer(modifier = Modifier.height(32.dp))


            Text(text = "Or sign in with:")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook__square),
                    contentDescription = "Facebook",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {

                        }
                )
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {

                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.twitter),
                    contentDescription = "Twitter",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {

                        }
                )
            }

        }
    }
}