package com.andreivanceadev.objectiverewards.navigation

sealed class ScreenNavigation(
    val navRoute: String,
) {

    object Dashboard : ScreenNavigation(navRoute = "dashboard")
    object TimeChart : ScreenNavigation(navRoute = "timeChart")
    object Graph : ScreenNavigation(navRoute = "graph")

    sealed class DashboardNavs(val route: String) {

        companion object {
            const val PARAM_OBJECTIVE_ID = "objectiveId"
        }

        object Home : DashboardNavs(route = "${Dashboard.navRoute}/Home")
        object Objective : DashboardNavs(
            route = "${Dashboard.navRoute}/Objective/{$PARAM_OBJECTIVE_ID}"
        )
    }
}