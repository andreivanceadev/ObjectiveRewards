package com.andreivanceadev.objectiverewards.navigation

sealed class ScreenNavigation(
    val navRoute: String,
) {

    data object Dashboard : ScreenNavigation(navRoute = "dashboard")
    data object Rewards : ScreenNavigation(navRoute = "rewards")
    data object Graph : ScreenNavigation(navRoute = "graph")

    sealed class DashboardNavs(val route: String) {
        data object Home : DashboardNavs(route = "${Dashboard.navRoute}/Home")
        data object AddNewObjective : DashboardNavs(route = "${Dashboard.navRoute}/AddNewObjective")
    }
}
