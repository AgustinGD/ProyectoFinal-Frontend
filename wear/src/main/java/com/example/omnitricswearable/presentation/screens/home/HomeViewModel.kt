package com.example.omnitricswearable.presentation.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.omnitricswearable.common.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()

    init {
        savedStateHandle.get<Int>(Constants.PARAM_UPLOAD_SUCCESS)?.let { uploadSuccess ->
            val showDialog = uploadSuccess != 0

            _showDialog.value = showDialog
        }
    }

    fun onShowDialogChange( newValue: Boolean){
        _showDialog.value = newValue
    }
}