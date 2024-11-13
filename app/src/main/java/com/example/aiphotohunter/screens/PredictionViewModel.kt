package com.example.aiphotohunter.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aiphotohunter.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

@HiltViewModel
class PredictionViewModel @Inject constructor() : ViewModel() {
    private val _capturedImage = MutableLiveData<Bitmap?>()
    val capturedImage: LiveData<Bitmap?> = _capturedImage

    private val _predictedName = MutableStateFlow<String?>(null)
    val predictedName: StateFlow<String?> = _predictedName

    private var retryCount = 0
    private val maxRetries = 2

    fun setCapturedImage(bitmap: Bitmap?) {
        _capturedImage.value = bitmap
    }

    fun incrementRetryCount() {
        retryCount++
    }

    fun resetRetryCount() {
        retryCount = 0
        _predictedName.value = null
    }

    fun shouldRetry(): Boolean {
        return retryCount < maxRetries
    }

    fun predictImageName() {
        viewModelScope.launch {
            try {
                val bitmap = _capturedImage.value
                if (bitmap != null) {
                    val generativeModel = GenerativeModel(
                        modelName = "gemini-1.5-flash",
                        apiKey = BuildConfig.apiKey
                    )

                    val inputContent = content {
                        image(bitmap)
                        text("Provide a single-word name for the item in this image.")
                    }

                    val response = generativeModel.generateContent(inputContent)
                    val predictedName =
                        response.text?.split(" ")?.firstOrNull()?.replace(Regex("[^A-Za-z]"), "")
                            ?: "Prediction failed"

                    _predictedName.value = predictedName
                    Log.d("PredictionViewModel", "Predicted name: $predictedName")
                } else {
                    _predictedName.value = "No image available"
                }
            } catch (e: Exception) {
                Log.e("PredictionViewModel", "Error predicting image name", e)
                _predictedName.value = "Prediction failed"
            }
        }
    }

    fun resetPrediction() {
        _predictedName.value = null
    }
}