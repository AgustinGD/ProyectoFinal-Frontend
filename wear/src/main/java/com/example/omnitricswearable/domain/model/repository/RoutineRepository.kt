package com.example.omnitricswearable.domain.model.repository

import com.example.omnitricswearable.data.remote.omnitrics.dto.completed.CompletedExerciseDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.RoutineDetailsDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.favourite.FavouriteDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.simple_routine.RoutineDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.time_spent_exercising.TimeSpentExercisingInHoursDto

interface RoutineRepository {
    suspend fun getRoutines(): List<RoutineDto>
    suspend fun getRoutineById(routineId: String): RoutineDetailsDto
    suspend fun getTimeSpentExercising(): TimeSpentExercisingInHoursDto
    suspend fun patchFavouriteARoutine(routineDetailsId: String, favouriteDto: FavouriteDto)
    suspend fun patchCompletedSets(completedExercises: List<CompletedExerciseDto>)
}