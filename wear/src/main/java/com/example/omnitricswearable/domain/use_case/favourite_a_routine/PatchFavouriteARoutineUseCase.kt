package com.example.omnitricswearable.domain.use_case.favourite_a_routine

import com.example.omnitricswearable.common.Resource
import com.example.omnitricswearable.data.remote.omnitrics.dto.favourite.FavouriteDto
import com.example.omnitricswearable.domain.model.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PatchFavouriteARoutineUseCase @Inject constructor(
    private val repository: RoutineRepository
) {
    operator fun invoke(routineDetailsId: String, favouriteDto: FavouriteDto): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.patchFavouriteARoutine(routineDetailsId, favouriteDto)

            emit(Resource.Success(favouriteDto.isFavourite))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error inesperado."))
        } catch (e: IOException) {
            emit(Resource.Error("No se pudo conectar al servidor. Chequear conexion a internet."))
        }
    }
}