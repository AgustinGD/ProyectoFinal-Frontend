package com.example.omnitricswearable.domain.graphs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.wear.compose.navigation.composable
import com.example.omnitricswearable.common.Constants
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsViewModel
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player.ExercisePlayerScreen
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player.set_statistic_progress.SetStatisticProgressScreen
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player.statistic_pickers.RepetitionsPickerScreen
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player.statistic_pickers.WeightPickerScreen

fun NavGraphBuilder.ExercisePlayerNavGraph(
    navController: NavHostController,
    routineDetailsScreenRoute: String
) {
    navigation(
        route = Graph.EXERCISE_PLAYER,
        startDestination = ExercisePlayerScreen.ExercisePlayer.route,
    ) {
        val setStatisticProgressScreenRoute =
            ExercisePlayerScreen.SetStatisticsProgressScreen.route + "/{${Constants.PARAM_EXERCISE_INDEX}}"

        composable(route = ExercisePlayerScreen.ExercisePlayer.route) { backStackEntry ->
            val routineDetailsViewModel = parentRoutineDetailsViewModel(
                backStackEntry,
                navController,
                routineDetailsScreenRoute
            )

            ExercisePlayerScreen(
                routineDetailsViewModel,
                onNavigateHomeClearAllRoutes(navController),
                onNavigateSetStatisticsProgress(navController),
                onNavigateWeightPicker(navController),
                onNavigateRepetitionPicker(navController)
            )
        }
        composable(
            route = setStatisticProgressScreenRoute,
            arguments = listOf(navArgument(Constants.PARAM_EXERCISE_INDEX) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val routineDetailsViewModel = parentRoutineDetailsViewModel(
                backStackEntry,
                navController,
                routineDetailsScreenRoute
            )
            val exerciseIndexParameter =
                backStackEntry.arguments?.getInt(Constants.PARAM_EXERCISE_INDEX)

            SetStatisticProgressScreen(routineDetailsViewModel, exerciseIndexParameter)
        }

        composable(route = ExercisePlayerScreen.WeightPickerScreen.route) { backStackEntry ->
            val routineDetailsViewModel = parentRoutineDetailsViewModel(
                backStackEntry,
                navController,
                routineDetailsScreenRoute
            )

            WeightPickerScreen(
                routineDetailsViewModel,
                onGoBack = onGoBack(navController)
            )
        }

        composable(route = ExercisePlayerScreen.RepetitionsPickerScreen.route) { backStackEntry ->
            val routineDetailsViewModel = parentRoutineDetailsViewModel(
                backStackEntry,
                navController,
                routineDetailsScreenRoute
            )

            RepetitionsPickerScreen(
                routineDetailsViewModel,
                onGoBack = onGoBack(navController)
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
private fun onNavigateHomeClearAllRoutes(navController: NavHostController) =
    { uploadSuccess: Int ->
        val homeRoute = HomeScreen.Home.route + "/$uploadSuccess"

        navController.navigate(homeRoute) {
            popUpTo(Graph.HOME) { inclusive = true }
            launchSingleTop = true
        }
    }

@Composable
private fun onNavigateSetStatisticsProgress(navController: NavHostController) =
    { exerciseIndex: Int -> navController.navigate(ExercisePlayerScreen.SetStatisticsProgressScreen.route + "/$exerciseIndex") }

@Composable
private fun onNavigateWeightPicker(navController: NavHostController) =
    { navController.navigate(ExercisePlayerScreen.WeightPickerScreen.route) }

@Composable
private fun onNavigateRepetitionPicker(navController: NavHostController) =
    { navController.navigate(ExercisePlayerScreen.RepetitionsPickerScreen.route) }

@Composable
private fun onGoBack(navController: NavHostController) = { navController.popBackStack() }

sealed class ExercisePlayerScreen(val route: String) {
    object ExercisePlayer : ExercisePlayerScreen(route = "exercise_player_route")
    object SetStatisticsProgressScreen :
        ExercisePlayerScreen(route = "set_statistics_progress_route")

    object WeightPickerScreen : ExercisePlayerScreen(route = "weight_picker_route")
    object RepetitionsPickerScreen : ExercisePlayerScreen(route = "repetitions_picker_route")
}