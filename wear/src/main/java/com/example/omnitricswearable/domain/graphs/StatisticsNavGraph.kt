package com.example.omnitricswearable.domain.graphs

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import androidx.wear.compose.navigation.composable
import com.example.omnitricswearable.presentation.screens.statistics.StatisticsScreen
import com.example.omnitricswearable.presentation.screens.statistics.time_spent_exercising.TimeSpentExercisingScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.StatisticsNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.STATISTICS,
        startDestination = StatisticsScreen.StatisticsScreen.route,
    ) {
        composable(route = StatisticsScreen.StatisticsScreen.route) {
            StatisticsScreen(
                onNavigateTimeSpentExercisingScreen = onNavigateTimeSpentExercisingScreen(
                    navController
                )
            )
        }

        composable(route = StatisticsScreen.TimeSpentExercising.route) {
            TimeSpentExercisingScreen()
        }
    }
}

@Composable
private fun onNavigateTimeSpentExercisingScreen(navController: NavHostController) =
    { navController.navigate(StatisticsScreen.TimeSpentExercising.route) }

sealed class StatisticsScreen() {
    object StatisticsScreen : RoutinesScreen(route = "statistics_route")
    object TimeSpentExercising : RoutinesScreen(route = "time_spent_exercising_route")
}