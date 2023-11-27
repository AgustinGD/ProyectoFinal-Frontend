package com.example.omnitricswearable.domain.use_case.get_routines

import com.example.omnitricswearable.common.Resource
import com.example.omnitricswearable.data.remote.omnitrics.dto.simple_routine.toRoutineDetails
import com.example.omnitricswearable.domain.model.entities.Routine
import com.example.omnitricswearable.domain.model.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

class GetRoutinesUseCase @Inject constructor(
    private val repository: RoutineRepository
) {
    operator fun invoke(): Flow<Resource<List<Routine>>> = flow {
        try {
            emit(Resource.Loading())
            val exe = repository.getRoutines()

            val routines = repository.getRoutines().map { it.toRoutineDetails() }
//            val routines = routineDummies()
            emit(Resource.Success(routines))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error inesperado."))
        } catch (e: IOException) {
            emit(Resource.Error("No se pudo conectar al servidor. Chequear conexion a internet."))
        }
    }
//
//    private fun routineDummies(): List<Routine> {
//        val dummyRoutineList = mutableListOf<Routine>()
//        val dummyRoutineNames = listOf<String>(
//            "Rutina JIJIJEJA",
//            "Rutina 2",
//            "Rutina Gym",
//            "Rutina Jhon",
//            "Rutina LMAO"
//        )
//
//        dummyRoutineNames.forEach { name -> dummyRoutineList.add(createDummyRoutine(name)) }
//
//        val dummyFavouriteRoutineNames = listOf<String>(
//            "FAVORITO",
//            "REBUENOJAJA",
//            "LA MEJOR RUTINA"
//        )
//
//        dummyFavouriteRoutineNames.forEach { name ->
//            dummyRoutineList.add(
//                createFavouriteDummyRoutine(name)
//            )
//        }
//
//        return dummyRoutineList
//    }
//
//    private fun createDummyRoutine(name: String): Routine {
//        return Routine(
//            id = name,
//            name = name,
//            isFavourite = false
//        )
//    }
//
//    private fun createFavouriteDummyRoutine(name: String): Routine {
//        return Routine(
//            id = name,
//            name = name,
//            isFavourite = true,
//        )
//    }
}