package com.example.omnitricswearable.common

import androidx.compose.ui.unit.dp

object Constants {
    //Para test local la IP tiene que ser: la IP privada de la PC que este hosteando el servidor.
    const val BASE_URL = "http://192.168.0.209:9090/api/"
    const val PARAM_ROUTINE_ID = "routineDetailsId"
    const val PARAM_EXERCISE_INDEX = "exerciseIndex"
    const val PARAM_UPLOAD_SUCCESS = "uploadSuccess"
    val ACTIONBAR_BUTTON_ICON_SIZE = 24.dp
    val ACTIONBAR_BUTTON_FRAME_SIZE = 50.dp
}