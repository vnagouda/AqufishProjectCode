package com.example.aqufishnewui20

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.aqufishnewui20.routes.MainScreen
import com.example.aqufishnewui20.screens.MainPageDashboard
import com.example.aqufishnewui20.ui.theme.AqufishNewUI20Theme
import com.example.aqufishnewui20.viewModels.SplashScreenViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {



    private val viewModel by viewModels<SplashScreenViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition(){
                !viewModel.isReady.value
            }
            setOnExitAnimationListener(){ screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView, View.SCALE_X, 0.4f, 0.0f
                )

                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView, View.SCALE_Y, 0.4f, 0.0f
                )

                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()

            }
        }


        enableEdgeToEdge()
        setContent {
            AqufishNewUI20Theme {
                //i have adde MainPageDashboard()
                //MainPageDashboardPreview()
            }
        }
    }
}