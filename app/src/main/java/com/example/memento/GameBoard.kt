package com.example.memento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameBoard(gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val spaceBetweenElements = 25.dp

    Column(
        verticalArrangement = Arrangement.spacedBy(spaceBetweenElements)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenElements)
        ) {
            Element(0)
            Element(1)
            Element(2)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenElements)
        ) {
            Element(3)
            Element(4)
            Element(5)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenElements)
        ) {
            Element(6)
            Element(7)
            Element(8)
        }
    }
}