package com.example.aiphotohunter.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aiphotohunter.screens.hunt.HuntViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HuntProgress(huntViewModel: HuntViewModel) {
    val itemsLeftText by huntViewModel.itemsLeftText.collectAsState()
    val scoreText by huntViewModel.scoreText.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = itemsLeftText,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = scoreText,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HuntProgressPreview() {
    val huntViewModel = HuntViewModel().apply {
        setSelectedLanguage("Türkçe")
        _currentItems.value = List(10) { "Item ${it + 1}" }
        _score.value = 150
        _itemsLeft.value = 5
    }
    HuntProgress(huntViewModel)
}