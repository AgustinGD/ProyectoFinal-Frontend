package com.example.omnitricswearable.data.remote.omnitrics

import com.example.omnitricswearable.data.remote.omnitrics.dto.completed.CompletedExerciseDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.RoutineDetailsDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.favourite.FavouriteDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.simple_routine.RoutineDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.time_spent_exercising.TimeSpentExercisingInHoursDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface OmnitricsApi {
    // Routine Details
    @GET("routine-details/")
    suspend fun getRoutines(): List<RoutineDto>

    @GET("routine-details/{routineDetailsId}")
    suspend fun getRoutineById(@Path("routineDetailsId") routineDetailsId: String): RoutineDetailsDto

    @PATCH("routine-details/{routineDetailsId}")
    suspend fun patchFavouriteARoutine(@Path("routineDetailsId") routineDetailsId: String, @Body favouriteDto: FavouriteDto)

    // Exercise Statistics
    @PATCH("exercise-statistics/sets")
    suspend fun patchCompletedSets(@Body completedExerciseSets: List<CompletedExerciseDto>)

    @GET("exercise-statistics/time-spent-exercising/weekly/in-hours")
    suspend fun getTimeSpentExercising(): TimeSpentExercisingInHoursDto

}