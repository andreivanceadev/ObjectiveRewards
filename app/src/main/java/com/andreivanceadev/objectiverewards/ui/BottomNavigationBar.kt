package com.andreivanceadev.objectiverewards.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
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
import com.andreivanceadev.objectiverewards.navigation.ScreenNavigation
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
@Preview(showSystemUi = true)
private fun PreviewBottomNavigationBar() {
    val screens = persistentListOf(
        BottomBarNav.Dashboard,
        BottomBarNav.TimeChart,
        BottomBarNav.Graph,
    )

    ObjectiveRewardsTheme {
        Scaffold(
            bottomBar = {
                BottomBar(
                    screens = screens,
                    currentRoute = BottomBarNav.Dashboard.route,
                    onNavItemClick = {},
                )
            },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                PlaceholderScreen(screenName = "Placeholder Screen")
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = persistentListOf(
        BottomBarNav.Dashboard,
        BottomBarNav.TimeChart,
        BottomBarNav.Graph,
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
        currentRoute = currentDestination?.route,
    )
}

@Composable
private fun BottomBar(
    screens: ImmutableList<BottomBarNav>,
    onNavItemClick: (route: String) -> Unit,
    currentRoute: String?,
) {
    NavigationBar {
        screens.forEach { screen ->

            val isSelected = currentRoute?.contains(screen.route) ?: false

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavItemClick(screen.route) },
                icon = {
                    Icon(screen.icon, contentDescription = screen.title)
                },
                label = { Text(text = screen.title) },
            )
        }
    }
}

sealed class BottomBarNav(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {

    data object Dashboard : BottomBarNav(
        route = ScreenNavigation.Dashboard.navRoute,
        title = "Dashboard",
        icon = Icons.Default.Home,
    )

    data object TimeChart : BottomBarNav(
        route = ScreenNavigation.TimeChart.navRoute,
        title = "Time Chart",
        icon = Icons.Default.PlayArrow,
    )

    data object Graph : BottomBarNav(
        route = ScreenNavigation.Graph.navRoute,
        title = "Graph View",
        icon = Icons.Default.ShoppingCart,
    )
}
