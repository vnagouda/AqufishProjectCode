package com.example.aqufishnewui20.routes

//import com.example.aqufishnewui20.screens.AlarmCardPreview
//import com.example.aqufishnewui20.screens.AlarmManagerUI
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aqufishnewui20.R
import com.example.aqufishnewui20.screens.LoginScreen
import com.example.aqufishnewui20.screens.MainPageDashboard
import com.example.aqufishnewui20.screens.MonitoringScreen
import com.example.aqufishnewui20.viewModels.MainViewModel
import com.google.firebase.auth.FirebaseAuth


data class BottomNavigationItem(
    val title: String,
    val selectedListener: ImageVector,
    val unselectedListener: ImageVector,
)


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    context: Context,
    navController: NavHostController,
    startDestination: String = "login",
    viewModel: MainViewModel
) {
    //val navController = rememberNavController()
    val isUserSignedIn by viewModel.isUserSignedIn.collectAsState()


    NavHost(navController = navController, startDestination = if (isUserSignedIn) "dashboard" else "login") {
        composable("login") {
            LoginScreen(viewModel = viewModel,navController = navController)

        }
        composable("dashboard") {

            //val context = LocalContext.current
            //val scrollState = rememberLazyListState()

            MainPageDashboard(viewModel = viewModel, navController = navController)


        }

        composable("live_monitoring/{ipAddress}") { backStackEntry ->
            val ipAddress = backStackEntry.arguments?.getString("ipAddress")
            MonitoringScreen(context = context, viewModel = viewModel, ipAddress = ipAddress, navController = navController)
        }

        composable("analytics") {
            Text(text = "Analytics Under Development")
        }

        composable("settings") {
            Text(text = "Settings Under Development")
        }
        composable("misc") {
            Text(text = "Misc Under Development")
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(context: Context, viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showScaffold = currentRoute != "login"
    val showFlotingIconButton = currentRoute == "dashboard"

    if (showScaffold) {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        //val image: ImageVector = ImageVector.vectorResource(id = R.drawable.baseline_ssid_chart_24)
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }

        val items = listOf(
            BottomNavigationItem(
                title = "Home",
                selectedListener = Icons.Filled.Home,
                unselectedListener = Icons.Outlined.Home
            ),

            BottomNavigationItem(
                title = "Analytics",
                selectedListener = ImageVector.vectorResource(id = R.drawable.baseline_ssid_chart_24) ,
                unselectedListener = ImageVector.vectorResource(id = R.drawable.outline_ssid_chart_24)
            ),

            BottomNavigationItem(
                title = "Settings",
                selectedListener = Icons.Filled.Settings,
                unselectedListener = Icons.Outlined.Settings
            ),

            BottomNavigationItem(
                title = "Misc",
                selectedListener = Icons.Filled.Menu,
                unselectedListener = Icons.Outlined.Menu
            ),
        )
        Scaffold(
            containerColor =  MaterialTheme.colorScheme.secondaryContainer,

            modifier = Modifier
                .fillMaxSize()
                //.background(MaterialTheme.colorScheme.error)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(

                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.logoaqufishhorizantal_02),
                            contentDescription = "App Logo",
                            modifier = Modifier.fillMaxSize(0.5f)
                        )
                    },

                    colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.onPrimaryContainer,MaterialTheme.colorScheme.tertiaryContainer,MaterialTheme.colorScheme.tertiaryContainer,MaterialTheme.colorScheme.tertiaryContainer),


                    actions = {
                        IconButton(onClick = {
                            FirebaseAuth.getInstance().signOut()
                            // Update the ViewModel state
                            viewModel.checkUserAuthState()
                            // Navigate to login screen
                            navController.navigate("login")
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Profile"
                            )

                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    //contentColor = MaterialTheme.colorScheme.tertiaryContainer
                ){
                    items.forEachIndexed { index, bottomNavigationItem ->
                        NavigationBarItem(
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                when (index){
                                    0 -> navController.navigate("dashboard")
                                    1 -> navController.navigate("analytics")
                                    2 -> navController.navigate("settings")
                                    3 -> navController.navigate("misc")
                                }
                            },
                            label = { Text(text = bottomNavigationItem.title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiaryContainer) },
                            //colors = BottomAppBarDefaults.(MaterialTheme.colorScheme.primary),
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) bottomNavigationItem.selectedListener else bottomNavigationItem.unselectedListener,
                                    contentDescription = bottomNavigationItem.title,
                                    tint = MaterialTheme.colorScheme.tertiaryContainer
                                )
                            }
                        )
                    }
                }
            },

            floatingActionButton = {
                if (showFlotingIconButton) {
                    FloatingActionButton(
                        onClick = {
                        // Handle button click
                    },
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }
            }
        ){ innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    //.verticalScroll(rememberScrollState()) // Add scroll state here
            ){
                item{

                    NavGraph( context, viewModel = viewModel, navController = navController)
                }
            }
        }
    } else {
        NavGraph(context, viewModel = viewModel, navController = navController)
    }
}
