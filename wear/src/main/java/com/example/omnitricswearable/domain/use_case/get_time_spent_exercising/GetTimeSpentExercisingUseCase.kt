package com.example.omnitricswearable.domain.use_case.get_time_spent_exercising

import com.example.omnitricswearable.common.Resource
import com.example.omnitricswearable.data.remote.omnitrics.dto.simple_routine.toRoutineDetails
import com.example.omnitricswearable.data.remote.omnitrics.dto.time_spent_exercising.TimeSpentExercisingInHoursDto
import com.example.omnitricswearable.domain.model.entities.Routine
import com.example.omnitricswearable.domain.model.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTimeSpentExercisingUseCase @Inject constructor(
    private val repository: RoutineRepository
) {
    operator fun invoke(): Flow<Resource<TimeSpentExercisingInHoursDto>> = flow {
        try {
            emit(Resource.Loading())
            val timeSpentExercising = repository.getTimeSpentExercising()

            emit(Resource.Success(timeSpentExercising))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error inesperado."))
        } catch (e: IOException) {
            emit(Resource.Error("No se pudo conectar al servidor. Chequear conexion a internet."))
        }
    }
}