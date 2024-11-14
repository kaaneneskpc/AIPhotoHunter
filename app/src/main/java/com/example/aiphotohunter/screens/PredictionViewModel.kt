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

    fun predictImageName(selectedLanguage: String) {
        viewModelScope.launch {
            try {
                val bitmap = _capturedImage.value
                if (bitmap != null) {
                    val generativeModel = GenerativeModel(
                        modelName = "gemini-1.5-flash",
                        apiKey = BuildConfig.apiKey
                    )

                    val prompt = when (selectedLanguage) {
                        "Türkçe" -> "Bu resimdeki öğenin tek kelimelik adını verin."
                        else -> "Provide a single-word name for the item in this image."
                    }

                    val inputContent = content {
                        image(bitmap)
                        text(prompt)
                    }

                    val response = generativeModel.generateContent(inputContent)
                    val regex = when (selectedLanguage) {
                        "Türkçe" -> "[^A-Za-zĞÜŞİÖÇğüşiöç]"
                        else -> "[^A-Za-z]"
                    }

                    val predictedName = response.text
                        ?.split(" ")
                        ?.firstOrNull()
                        ?.replace(Regex(regex), "")
                        ?: if (selectedLanguage == "Türkçe") "Tahmin başarısız oldu" else "Prediction failed"

                    _predictedName.value = predictedName
                    Log.d("PredictionViewModel", "Predicted name: $predictedName")
                } else {
                    _predictedName.value = if (selectedLanguage == "Türkçe") "Kullanılabilir resim yok" else "No image available"
                }
            } catch (e: Exception) {
                Log.e("PredictionViewModel", "Error predicting image name", e)
                _predictedName.value = if (selectedLanguage == "Türkçe") "Tahmin başarısız oldu" else "Prediction failed"
            }
        }
    }

    fun resetPrediction() {
        _predictedName.value = null
    }
}