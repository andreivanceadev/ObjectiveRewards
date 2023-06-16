package com.andreivanceadev.objectiverewards.navigation

sealed class ScreenNavigation(
    val navRoute: String,
) {

    object Dashboard : ScreenNavigation(navRoute = "dashboard")
    object TimeChart : ScreenNavigation(navRoute = "timeChart")
    object Graph : ScreenNavigation(navRoute = "graph")

    sealed class DashboardNavs(val route: String) {
        object Home : DashboardNavs(route = "${Dashboard.navRoute}/Home")
        object AddNewObjective : DashboardNavs(route = "${Dashboard.navRoute}/AddNewObjective")
    }

}