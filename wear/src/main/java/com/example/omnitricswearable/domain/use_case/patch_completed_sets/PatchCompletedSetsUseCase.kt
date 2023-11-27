package com.example.omnitricswearable.domain.use_case.patch_completed_sets

import com.example.omnitricswearable.common.Resource
import com.example.omnitricswearable.data.remote.omnitrics.dto.completed.CompletedExerciseDto
import com.example.omnitricswearable.domain.model.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PatchCompletedSetsUseCase @Inject constructor(
    private val repository: RoutineRepository
) {
    operator fun invoke(completedExercises: List<CompletedExerciseDto>): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.patchCompletedSets(completedExercises)

            emit(Resource.Success("Success"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error inesperado."))
        } catch (e: IOException) {
            emit(Resource.Error("No se pudo conectar al servidor. Chequear conexion a internet."))
        }
    }
}