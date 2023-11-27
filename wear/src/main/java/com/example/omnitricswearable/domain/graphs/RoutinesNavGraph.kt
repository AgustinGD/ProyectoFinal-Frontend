package com.example.omnitricswearable.domain.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.wear.compose.navigation.composable
import com.example.omnitricswearable.common.Constants
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsScreen
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsViewModel
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_list.ExerciseListScreen
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player.ExercisePlayerScreen
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player.set_statistic_progress.SetStatisticProgressScreen
import com.example.omnitricswearable.presentation.screens.routine_list.RoutineListScreen

fun NavGraphBuilder.RoutinesNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.ROUTINES,
        startDestination = RoutinesScreen.RoutineList.route,
    ) {
        val routineDetailsScreenRoute =
            RoutinesScreen.RoutineDetailsScreen.route + "/{${Constants.PARAM_ROUTINE_ID}}"

        ExercisePlayerNavGraph(navController, routineDetailsScreenRoute)

        composable(route = RoutinesScreen.RoutineList.route) {
            RoutineListScreen(onNavigateRoutineDetails = onNavigateRoutineDetails(navController))
        }

        composable(
            route = routineDetailsScreenRoute,
            arguments = listOf(navArgument(Constants.PARAM_ROUTINE_ID) {
                type = NavType.StringType
            })
        ) {
            RoutineDetailsScreen(onNavigateExerciseList = onNavigateExerciseList(navController))
        }
        composable(route = RoutinesScreen.ExerciseList.route) { backStackEntry ->
            val routineDetailsViewModel = parentRoutineDetailsViewModel(
                backStackEntry,
                navController,
                routineDetailsScreenRoute
            )

            ExerciseListScreen(
                onNavigateExercisePlayer = onNavigateExercisePlayer(navController),
                routineDetailsViewModel
            )
        }
    }
}

@Composable
private fun parentRoutineDetailsViewModel(
    backStackEntry: NavBackStackEntry,
    navController: NavHostController,
    routineDetailsScreenRoute: String
): RoutineDetailsViewModel {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(routineDetailsScreenRoute)
    }
    val routineDetailsViewModel = hiltViewModel<RoutineDetailsViewModel>(parentEntry)

    return routineDetailsViewModel
}

@Composable
private fun onNavigateExerciseList(navController: NavHostController) =
    { navController.navigate(RoutinesScreen.ExerciseList.route) }

@Composable
private fun onNavigateExercisePlayer(navController: NavHostController) =
    { navController.navigate(Graph.EXERCISE_PLAYER) }

@Composable
private fun onNavigateRoutineDetails(navController: NavHostController) = { routineID: String ->
    navController.navigate(RoutinesScreen.RoutineDetailsScreen.route + "/$routineID")
}

sealed class RoutinesScreen(val route: String) {
    object RoutineList : RoutinesScreen(route = "routine_list_route")
    object RoutineDetailsScreen : RoutinesScreen(route = "routine_details_route")
    object ExerciseList : RoutinesScreen(route = "exercise_list_route")
}