package com.example.aiphotohunter.screens.hunt

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.aiphotohunter.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HuntViewModel @Inject constructor() : ViewModel() {
    private val _selectedLocation = MutableStateFlow<String?>(null)
    val selectedLocation: StateFlow<String?> = _selectedLocation

    private val _currentItems = MutableStateFlow<List<String>>(emptyList())
    val currentItems: StateFlow<List<String>> = _currentItems

    private val _currentItem = MutableStateFlow<String?>(null)
    val currentItem: StateFlow<String?> = _currentItem

    private val _itemsLeft = MutableStateFlow(0)
    val itemsLeft: StateFlow<Int> = _itemsLeft

    val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _correctAnswers = MutableStateFlow(0)
    val correctAnswers: StateFlow<Int> = _correctAnswers

    private val _selectedLanguage = MutableStateFlow<String?>("English")
    val selectedLanguage: StateFlow<String?> = _selectedLanguage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentIndex = 0
    var onFinished: (() -> Unit)? = null

    fun setScore(reward: Int) {
        _score.value += reward
    }

    fun setSelectedLanguage(language: String) {
        _selectedLanguage.value = language
        // Refresh the items based on the new language
        _selectedLocation.value?.let { location ->
            generateItems(location)
        }
    }

    fun incrementCorrectAnswers() {
        _correctAnswers.value += 1
    }

    fun selectLocation(location: String) {
        _selectedLocation.value = location
        generateItems(location)
    }

    private fun generateItems(location: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val randomNumber = (1..10000).random()
                val language = _selectedLanguage.value ?: "English"

                val prompt = when (language) {
                    "Türkçe" -> when (location) {
                        "Home" -> "Evde yaygın olarak bulunan 10 benzersiz tek kelimelik öğe listeleyin. Giriş cümleleri olmadan, sadece virgülle ayrılmış öğeleri listeleyin. Örnek: Koltuk, Masa, vb. Rastgele sayı: $randomNumber"
                        "Outside" -> "Dışarıda yaygın olarak bulunan 10 benzersiz tek kelimelik öğe listeleyin. Giriş cümleleri olmadan, sadece virgülle ayrılmış öğeleri listeleyin. Örnek: Ağaç, Taş, vb. Rastgele sayı: $randomNumber"
                        else -> "Virgülle ayrılmış 10 benzersiz tek kelimelik öğe listeleyin. Rastgele sayı: $randomNumber"
                    }

                    else -> when (location) {
                        "Home" -> "List 10 unique single-word items that are commonly found in a home. Just list the items separated by commas, without any introductory sentences. Example: Couch, Table, etc. Random number: $randomNumber"
                        "Outside" -> "List 10 unique single-word items that are commonly found outside. Just list the items separated by commas, without any introductory sentences. Example: Tree, Rock, etc. Random number: $randomNumber"
                        else -> "List 10 unique single-word items, separated by commas. Random number: $randomNumber"
                    }
                }

                val generativeModel = GenerativeModel(
                    modelName = "gemini-1.5-flash",
                    apiKey = BuildConfig.apiKey
                )

                val regex = when (language) {
                    "Türkçe" -> "[^a-zA-ZiİçÇşŞğĞÜüÖö ]"
                    else -> "[^A-Za-z ]"
                }

                val response = generativeModel.generateContent(prompt)
                val items = response.text
                    ?.split(",")
                    ?.map { it.trim() }
                    ?.filter { it.isNotBlank() }
                    ?.map { it.replace(Regex(regex), "").trim() }

                Log.d("HuntViewModel", "Generated items for $location: $items")
                if (items != null) {
                    _currentItems.value = items
                    _itemsLeft.value = items.size
                    currentIndex = 0
                    pickNextItem()
                } else {
                    Log.e("HuntViewModel", "Failed to generate items: No items generated")
                    // Handle the case where no items are generated
                    _currentItems.value = emptyList()
                    _itemsLeft.value = 0
                }
            } catch (e: Exception) {
                Log.e("HuntViewModel", "Error generating items", e)
                // Handle the error appropriately
                _currentItems.value = emptyList()
                _itemsLeft.value = 0
            } finally {
                _isLoading.value = false // Stop loading
            }
        }
    }

    fun pickNextItem() {
        viewModelScope.launch {
            if (currentIndex < _currentItems.value.size) {
                _currentItem.value = _currentItems.value[currentIndex]
                _itemsLeft.value = _currentItems.value.size - currentIndex
                Log.d("HuntViewModel", "Picked item: ${_currentItem.value}")
                currentIndex++
            } else {
                onFinished?.invoke()
                _currentItem.value = "No more items"
                _itemsLeft.value = 0
                Log.d("HuntViewModel", "No more items to pick")
            }
        }
    }

    fun reset() {
        _selectedLocation.value = null
        _currentItems.value = emptyList()
        _currentItem.value = null
        _itemsLeft.value = 0
        _score.value = 0
        currentIndex = 0
        _correctAnswers.value = 0

    }
}