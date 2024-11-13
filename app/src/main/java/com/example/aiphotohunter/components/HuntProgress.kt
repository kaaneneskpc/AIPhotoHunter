package com.example.aiphotohunter.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.aiphotohunter.screens.hunt.HuntViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HuntProgress(huntViewModel: HuntViewModel) {
    val itemsLeft = huntViewModel.itemsLeft.collectAsState()
    val score = huntViewModel.score.collectAsState()
    val totalItems = huntViewModel.currentItems.value.size

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Items left: ${itemsLeft.value}",
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = "${score.value}/${totalItems * 50}",
            style = MaterialTheme.typography.titleSmall
        )
    }
}