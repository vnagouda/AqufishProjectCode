package com.example.aqufishnewui20.routes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aqufishnewui20.R
import com.example.aqufishnewui20.screens.FishFarmApp
import com.example.aqufishnewui20.screens.LoginScreen
import com.example.aqufishnewui20.screens.MainPageDashboard
import com.example.aqufishnewui20.screens.MonitoringScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


data class BottomNavigationItem(
    val title: String,
    val selectedListener: ImageVector,
    val unselectedListener: ImageVector,
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    innerPadding: PaddingValues,
    navController: NavHostController,
    startDestination: String = "login",
) {
    //val navController = rememberNavController()

    Column(
        modifier = Modifier.padding(innerPadding)
    ) {
        NavHost(navController = navController, startDestination = startDestination) {
            composable("login") {
                LoginScreen(navController = navController)
            }
            composable("dashboard") {
                MainPageDashboard(navController = navController)
            }

            composable(
                "live_monitoring/{ipAddress}",
                arguments = listOf(navArgument("ipAddress") { type = NavType.StringType })
            ) { backStackEntry ->
                val ipAddress = backStackEntry.arguments?.getString("ipAddress")
                MonitoringScreen(navController = navController, ipaddress = ipAddress)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showScaffold = currentRoute != "login"

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
            containerColor =  MaterialTheme.colorScheme.onPrimary,
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



                    actions = {
                        IconButton(onClick = { navController.navigate("login") }) {
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
                NavigationBar(){
                    items.forEachIndexed { index, bottomNavigationItem ->
                        NavigationBarItem(
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                if(index == 0){
                                    navController.navigate("dashboard")
                                }

                            },
                            label = { Text(text = bottomNavigationItem.title) },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) bottomNavigationItem.selectedListener else bottomNavigationItem.unselectedListener,
                                    contentDescription = bottomNavigationItem.title
                                )
                            }
                        )
                    }
                }
            },

            floatingActionButton = {
                FloatingActionButton(onClick = { /* Todo*/ }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ){ innerPadding ->
            NavGraph(innerPadding, navController = navController)
        }
    } else {
        NavGraph(innerPadding = PaddingValues(),navController = navController)
    }
}
