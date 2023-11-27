package com.example.omnitricswearable.domain.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost

@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    SwipeDismissableNavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME
    ) {
        HomeNavGraph(navController = navController)
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val HOME = "home_graph"
    const val ROUTINES = "routines_graph"
    const val STATISTICS = "statistics_graph"
    const val EXERCISE_PLAYER = "exercise_player_graph"
}