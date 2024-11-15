package com.example.aiphotohunter.screens.hunt

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.aiphotohunter.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class HuntViewModel @Inject constructor() : ViewModel() {
    private val _selectedLocation = MutableStateFlow<String?>(null)
    val selectedLocation: StateFlow<String?> = _selectedLocation

    val _currentItems = MutableStateFlow<List<String>>(emptyList())
    val currentItems: StateFlow<List<String>> = _currentItems

    private val _currentItem = MutableStateFlow<String?>(null)
    val currentItem: StateFlow<String?> = _currentItem

    val _itemsLeft = MutableStateFlow(0)
    val itemsLeft: StateFlow<Int> = _itemsLeft

    val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    val _correctAnswers = MutableStateFlow(0)
    val correctAnswers: StateFlow<Int> = _correctAnswers

    private val _selectedLanguage = MutableStateFlow<String?>("English")
    val selectedLanguage: StateFlow<String?> = _selectedLanguage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentIndex = 0
    var onFinished: (() -> Unit)? = null

    private val _reward = MutableStateFlow(50)
    val reward: StateFlow<Int> = _reward

    private val totalQuestions = 10

    val finishText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Av bitti!" else "The hunt is over!"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "The hunt is over!")

    val yourScoreText: StateFlow<String> = combine(selectedLanguage, score) { lang, scoreValue ->
        if (lang == "Türkçe") "Skorun: $scoreValue" else "Your score: $scoreValue"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Your score: 0")

    val winRateText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Başarı Oranı:" else "Win Rate:"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Win Rate:")

    val playAgainText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Tekrar oyna" else "Play again"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Play again")

    val winPercentage: StateFlow<Float> = correctAnswers.map { correct ->
        correct.toFloat() / totalQuestions.toFloat()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0f)

    val winRatePercentage: StateFlow<Int> = winPercentage.map { percentage ->
        (percentage * 100).toInt()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val emoji: StateFlow<String> = winRatePercentage.map { percentage ->
        when (percentage) {
            in 0..20 -> "😞"
            in 21..40 -> "😐"
            in 41..60 -> "🙂"
            in 61..80 -> "😃"
            in 81..100 -> "🤩"
            else -> "🤠"
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "🤠")


    private val _languages = listOf("English", "Türkçe")
    val languages: List<String> get() = _languages

    val galleryIconDescription: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Galeriden fotoğraf seç" else "Select photo from gallery"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Select photo from gallery")

    val languageLabel: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Dil" else "Language"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Language")


    val selectLanguagePlaceholder: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Dil seçin" else "Select Language"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Select Language")


    val nextItemText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Sonraki bulunacak öğe:" else "Next item to find:"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Next item to find:")


    val noItemText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Öğe yok" else "No item"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "No item")


    val skipButtonText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Atla" else "Skip"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Skip")


    val takePhotoButtonText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Fotoğraf çek" else "Take photo"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Take photo")

    val readyText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Avınız hazır!" else "Your hunt is ready!"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Your hunt is ready!")


    val bringItOnText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Başlayalım!" else "Bring it on!"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Bring it on!")


    val readyEmoji: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "🎯" else "🤖"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "🤖")


    val successEmoji: StateFlow<String> = selectedLanguage.map {
        "🤩"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "🤩")


    val foundItText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Bulundu!" else "You found it!"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "You found it!")


    val rewardText: StateFlow<String> = combine(selectedLanguage, reward) { lang, rewardValue ->
        if (lang == "Türkçe") "+$rewardValue" else "+$rewardValue"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "+0")


    val nextItemButtonText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Sonraki öğe" else "Next item"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Next item")

    val failureEmoji: StateFlow<String> = selectedLanguage.map {
        "🫠"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "🫠")

    val itemsLeftText: StateFlow<String> =
        combine(selectedLanguage, itemsLeft) { lang, itemsLeftValue ->
            if (lang == "Türkçe") {
                "Kalan öğeler: $itemsLeftValue"
            } else {
                "Items left: $itemsLeftValue"
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, "Items left: 0")

    val errorEmoji: StateFlow<String> = selectedLanguage.map {
        "🫠"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "🫠")


    val errorMessage: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Yapay zeka mola vermeye karar verdi..." else "AI decided to take a break..."
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "AI decided to take a break...")


    val tryAgainText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Tekrar dene" else "Try again"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Try again")

    val loadingEmoji: StateFlow<String> = selectedLanguage.map {
        "🤖"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "🤖")

    val loadingMessage: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "AI avınızı hazırlıyor..." else "AI is preparing your hunt..."
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "AI is preparing your hunt...")

    val loadingValidationMessage: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "AI fotoğrafınızı doğruluyor..." else "AI is validating the photo..."
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "AI is validating the photo...")

    val homeTitle: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "  Ev  " else "  Home  "
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "  Home  ")

    val outsideTitle: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "  Dışarı  " else " Outside"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, " Outside")

    val startHuntingButtonText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Avlanmaya Başla" else "Start Hunting"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Start Hunting")

    val logoDescription: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Üst Resim" else "Top Image"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Top Image")

    val locationPrompt: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Avlanma konumunuzu seçin" else "Choose your hunting location"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Choose your hunting location")

    val shareResultText: StateFlow<String> = selectedLanguage.map { lang ->
        if (lang == "Türkçe") "Sonucu Paylaş" else "Share Result"
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "Share Result")


    val scoreText: StateFlow<String> =
        combine(selectedLanguage, score, currentItems) { lang, scoreValue, currentItemsList ->
            val totalItems = currentItemsList.size
            val totalScore = totalItems * _reward.value
            if (lang == "Türkçe") {
                "$scoreValue/$totalScore"
            } else {
                "$scoreValue/$totalScore"
            }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, "0/0")

    fun getFailureMessage(shouldRetry: Boolean): String {
        val lang = selectedLanguage.value ?: "English"
        return if (lang == "Türkçe") {
            if (shouldRetry) {
                "Hayır, öyle görünmüyor."
            } else {
                "Hayır, öyle görünmüyor. Tekrar deneme hakkınız tükendi. Sonraki öğeye geçiliyor."
            }
        } else {
            if (shouldRetry) {
                "Nope, doesn't look like it."
            } else {
                "Nope, doesn't look like it. You've reached your retry limit. Moving to the next item."
            }
        }
    }

    fun getRetryButtonText(shouldRetry: Boolean): String {
        val lang = selectedLanguage.value ?: "English"
        return if (shouldRetry) {
            if (lang == "Türkçe") "Tekrar dene" else "Try again"
        } else {
            if (lang == "Türkçe") "Sonraki" else "Next"
        }
    }

    fun applyReward() {
        _score.value += _reward.value
    }

    fun setSelectedLanguage(language: String) {
        _selectedLanguage.value = language
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
                    "Türkçe" -> "[^a-zA-ZiİçÇşŞğĞÜüÖöıIuUOo ]"
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