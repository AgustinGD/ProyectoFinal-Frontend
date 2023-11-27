package com.example.omnitricswearable.domain.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.wear.compose.navigation.composable
import com.example.omnitricswearable.common.Constants
import com.example.omnitricswearable.presentation.screens.calendar.CalendarScreen
import com.example.omnitricswearable.presentation.screens.home.HomeScreen

fun NavGraphBuilder.HomeNavGraph(
    navController: NavHostController
) {
    val homeScreenRoute = HomeScreen.Home.route + "/{${Constants.PARAM_UPLOAD_SUCCESS}}"

    navigation(
        route = Graph.HOME,
        startDestination = homeScreenRoute
    ) {

        RoutinesNavGraph(navController = navController)
        StatisticsNavGraph(navController = navController)

        composable(
            route = homeScreenRoute,
            arguments = listOf(navArgument(Constants.PARAM_UPLOAD_SUCCESS) {
                type = NavType.IntType
            })
        ) {
            HomeScreen(
                onNavigateRoutineMenu = onClickRoutineMenu(navController),
                onNavigateCalendarScreen = onNavigateCalendarScreen(navController),
                onNavigateStatisticsScreen = onNavigateStatisticsScreen(navController = navController)
            )
        }

        composable(route = HomeScreen.Calendar.route) {
            CalendarScreen()
        }
    }
}


@Composable
private fun onClickRoutineMenu(navController: NavHostController) =
    { navController.navigate(Graph.ROUTINES) }

@Composable
private fun onNavigateCalendarScreen(navController: NavHostController) =
    { navController.navigate(HomeScreen.Calendar.route) }

@Composable
private fun onNavigateStatisticsScreen(navController: NavHostController) =
    { navController.navigate(Graph.STATISTICS) }

sealed class HomeScreen(val route: String) {
    object Home : HomeScreen(route = "home_route")
    object Calendar : HomeScreen(route = "calendar_route")
}