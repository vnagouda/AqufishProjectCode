package com.example.aqufishnewui20

//import com.example.aqufishnewui20.screens.Screen1
//import com.example.aqufishnewui20.workers.scheduleAlarm
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.work.WorkManager
import com.example.aqufishnewui20.routes.MainScreen
import com.example.aqufishnewui20.screens.AppDatabase
import com.example.aqufishnewui20.screens.MonitoringScreen
import com.example.aqufishnewui20.ui.theme.AppTheme
import com.example.aqufishnewui20.viewModels.MainViewModel
import com.example.aqufishnewui20.viewModels.MainViewModelFactory
import com.example.aqufishnewui20.viewModels.SplashScreenViewModel

class MainActivity : ComponentActivity() {



    private val viewModel by viewModels<SplashScreenViewModel>()



    private val workManager: WorkManager by lazy { WorkManager.getInstance(this) }
    private val appDatabase: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            alarmDao = appDatabase.alarmDao(),
            workManager = workManager,
            context = this
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
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
            AppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ){
                    MainScreen(context = this, viewModel = mainViewModel)

                    //MonitoringScreen(context = this, viewModel = mainViewModel, navController = null)

                }
                //i have adde MainPageDashboard()
                //MainPageDashboardPreview()

            }
        }
    }
}