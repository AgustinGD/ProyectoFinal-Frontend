package com.example.omnitricswearable.domain.use_case.get_routine

import com.example.omnitricswearable.common.Resource
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.Day
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.ExerciseInfo
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.ExerciseStatistics
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.toRoutineDetails
import com.example.omnitricswearable.domain.model.entities.RoutineDetails
import com.example.omnitricswearable.domain.model.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GetRoutineUseCase @Inject constructor(
    private val repository: RoutineRepository
) {

    operator fun invoke(routineId: String): Flow<Resource<RoutineDetails>> = flow {
        try {
            emit(Resource.Loading())
            val routine = repository.getRoutineById(routineId).toRoutineDetails()
//            val routine = createDummyRoutine(routineId)
            emit(Resource.Success(routine))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error inesperado."))
        } catch (e: IOException) {
            emit(Resource.Error("No se pudo conectar al servidor. Chequear conexion a internet."))
        }
    }

    private fun createDummyRoutine(routineID: String): RoutineDetails {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("YYYY-MM-DD HH:MM:SS")
        val dateTime = dateFormat.format(calendar.time)

        val dummyDays = createDummyDayList()

        val dummyRoutine = RoutineDetails(
            id = routineID,
            name = "OH HI MARK",
            isFavourite = true,
            dayList = dummyDays,
            updatedDate = dateTime
        )

        return dummyRoutine
    }

    private fun createDummyDayList(): List<Day> {
        val dummyDayList = mutableListOf<Day>()
        dummyDayList.add(dayOne())
        dummyDayList.add(dayTwo())
        dummyDayList.add(dayThree())
        dummyDayList.add(dayFour())

        return dummyDayList
    }

    private fun dayOne(): Day {
        val exerciseList = mutableListOf<ExerciseStatistics>()

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("Peso Muerto"),
                maxSets = 4,
                position = 0,
                setList = emptyList()
            )
        )

        return Day(
            id = "1",
            description = "Piernas",
            dayPosition = 1,
            exerciseList = exerciseList
        )
    }

    private fun dayTwo(): Day {
        val exerciseList = mutableListOf<ExerciseStatistics>()

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("Bicep Scot"),
                maxSets = 4,
                position = 0,
                setList = emptyList()
            )
        )

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("Tricep Frances"),
                maxSets = 4,
                position = 1,
                setList = emptyList()
            )
        )

        return Day(
            id = "1",
            description = "Brazos",
            dayPosition = 2,
            exerciseList = exerciseList
        )
    }

    private fun dayThree(): Day {
        val exerciseList = mutableListOf<ExerciseStatistics>()

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("Press barra"),
                maxSets = 4,
                position = 0,
                setList = emptyList()
            )
        )

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("Aperturas con Mancuerna"),
                maxSets = 4,
                position = 1,
                setList = emptyList()
            )
        )

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("Remos Polea Baja"),
                maxSets = 4,
                position = 2,
                setList = emptyList()
            )
        )

        return Day(
            id = "1",
            description = "Pecho/Espalda",
            dayPosition = 3,
            exerciseList = exerciseList
        )
    }

    private fun dayFour(): Day {
        val exerciseList = mutableListOf<ExerciseStatistics>()

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("Vuelos Laterales"),
                maxSets = 4,
                position = 0,
                setList = emptyList()
            )
        )

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("Face Pull"),
                maxSets = 4,
                position = 1,
                setList = emptyList()
            )
        )

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("V Up"),
                maxSets = 4,
                position = 2,
                setList = emptyList()
            )
        )

        exerciseList.add(
            ExerciseStatistics(
                id = "1",
                exerciseInfo = createExerciseInfo("Abdominales Polea Soga"),
                maxSets = 4,
                position = 3,
                setList = emptyList()
            )
        )

        return Day(
            id = "1",
            description = "Hombros/Abdominales",
            dayPosition = 4,
            exerciseList = exerciseList
        )
    }

    private fun createExerciseInfo(name: String): ExerciseInfo {
        return ExerciseInfo(
            id = "1",
            name = name,
            description = "Soy un ejercicio XD!"
        )
    }
}