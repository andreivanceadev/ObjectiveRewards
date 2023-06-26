@file:OptIn(ExperimentalMaterial3Api::class)

package com.andreivanceadev.objectiverewards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.andreivanceadev.dashboard.ui.DashboardScreen
import com.andreivanceadev.designsystem.composables.PlaceholderScreen
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme
import com.andreivanceadev.objectiverewards.navigation.ScreenNavigation
import com.andreivanceadev.objectiverewards.ui.BottomBarNav
import com.andreivanceadev.objectiverewards.ui.BottomNavigationBar
import com.andreivanceadev.objectives.ui.AddObjectiveScreen
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
                    },
                ) { innerPadding ->

                    NavHost(
                        modifier = Modifier
                            .padding(innerPadding),
                        navController = navController,
                        startDestination = BottomBarNav.Dashboard.route
                    ) {
                        navigation(
                            route = BottomBarNav.Dashboard.route,
                            startDestination = ScreenNavigation.DashboardNavs.Home.route
                        ) {
                            composable(route = ScreenNavigation.DashboardNavs.Home.route) {
                                DashboardScreen(onAddNewObjective = {
                                    navController.navigate(ScreenNavigation.DashboardNavs.AddNewObjective.route)
                                })
                            }
                            composable(route = ScreenNavigation.DashboardNavs.AddNewObjective.route) {
                                AddObjectiveScreen(
                                    onFinish = { navController.popBackStack() }
                                )
                            }
                        }
                        composable(route = BottomBarNav.TimeChart.route) {
                            PlaceholderScreen(screenName = "Time Chart")
                        }
                        composable(route = BottomBarNav.Graph.route) {
                            PlaceholderScreen(screenName = "Graph View")
                        }
                    }
                }
            }
        }
    }
}