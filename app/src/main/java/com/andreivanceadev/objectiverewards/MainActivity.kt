@file:OptIn(ExperimentalMaterial3Api::class)

package com.andreivanceadev.objectiverewards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreivanceadev.designsystem.composables.PlaceholderScreen
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.objectiverewards.ui.BottomBarScreen
import com.andreivanceadev.objectiverewards.ui.BottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ObjectiveRewardsTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->

                    NavHost(
                        modifier = Modifier
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = BottomBarScreen.Dashboard.route
                    ) {
                        composable(route = BottomBarScreen.Dashboard.route) {
                            PlaceholderScreen(screenName = "Dashboard")
                        }
                        composable(route = BottomBarScreen.TimeChart.route) {
                            PlaceholderScreen(screenName = "Time Chart")
                        }
                        composable(route = BottomBarScreen.Graph.route) {
                            PlaceholderScreen(screenName = "Graph View")
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ObjectiveRewardsTheme {
        Greeting("Android")
    }
}