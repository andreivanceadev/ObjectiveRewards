package com.andreivanceadev.objectiverewards.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.andreivanceadev.designsystem.composables.PlaceholderScreen
import com.andreivanceadev.designsystem.theme.ObjectiveRewardsTheme

@Composable
@Preview(showSystemUi = true)
private fun PreviewBottomNavigationBar() {
    val screens = listOf(
        BottomBarScreen.Dashboard,
        BottomBarScreen.TimeChart,
        BottomBarScreen.Graph
    )

    ObjectiveRewardsTheme {
        Scaffold(
            bottomBar = {
                BottomBar(
                    screens = screens,
                    currentRoute = BottomBarScreen.Dashboard.route,
                    onNavItemClick = {}
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                PlaceholderScreen(screenName = "Placeholder Screen")
            }
        }

    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val screens = listOf(
        BottomBarScreen.Dashboard,
        BottomBarScreen.TimeChart,
        BottomBarScreen.Graph
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomBar(
        screens = screens,
        onNavItemClick = { route ->
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        },
        currentRoute = currentDestination?.route
    )
}

@Composable
private fun BottomBar(
    screens: List<BottomBarScreen>,
    onNavItemClick: (route: String) -> Unit,
    currentRoute: String?
) {
    NavigationBar {
        screens.map { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = { onNavItemClick(screen.route) },
                icon = {
                    Icon(screen.icon, contentDescription = screen.title)
                },
                label = { Text(text = screen.title) }
            )
        }
    }
}

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Dashboard : BottomBarScreen(
        route = "dashboard",
        title = "Dashboard",
        icon = Icons.Default.Home
    )

    object TimeChart : BottomBarScreen(
        route = "time",
        title = "Time Chart",
        icon = Icons.Default.PlayArrow
    )

    object Graph : BottomBarScreen(
        route = "graph",
        title = "Graph View",
        icon = Icons.Default.ShoppingCart
    )

}