package com.example.omnitricswearable.data.repository

import com.example.omnitricswearable.data.remote.omnitrics.OmnitricsApi
import com.example.omnitricswearable.data.remote.omnitrics.dto.completed.CompletedExerciseDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.RoutineDetailsDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.favourite.FavouriteDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.simple_routine.RoutineDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.time_spent_exercising.TimeSpentExercisingInHoursDto
import com.example.omnitricswearable.domain.model.repository.RoutineRepository
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val api: OmnitricsApi
) : RoutineRepository {
    override suspend fun getRoutines(): List<RoutineDto> {
        return api.getRoutines()
    }

    override suspend fun getRoutineById(id: String): RoutineDetailsDto {
        return api.getRoutineById(id)
    }

    override suspend fun getTimeSpentExercising(): TimeSpentExercisingInHoursDto {
        return api.getTimeSpentExercising()
    }

    override suspend fun patchFavouriteARoutine(routineDetailsId: String, favouriteDto: FavouriteDto) {
        return api.patchFavouriteARoutine(routineDetailsId, favouriteDto)
    }

    override suspend fun patchCompletedSets(completedExercises: List<CompletedExerciseDto>) {
        return api.patchCompletedSets(completedExercises)
    }
}