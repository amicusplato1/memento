package com.example.memento

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Element(index: Int, gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()
    val bgColor by animateColorAsState(
        targetValue = if (gameUiState.highlightedElements[index]) gameUiState.highlightColor else gameUiState
            .elementColor,
        animationSpec = tween(500),
        label = ""
    )
    val borderColor by animateColorAsState(
        targetValue = if (gameUiState.highlightedElements[index]) gameUiState.highlightColor else Color.Transparent,
        animationSpec = tween(1500),
        label = ""
    )

    Box(
        modifier = Modifier
            .size(75.dp)
            .clip(RoundedCornerShape(10))
            .background(color = bgColor)
            .border(width = 5.dp, color = borderColor)
            .clickable(onClick = { gameViewModel.checkElement(index) })
    )
}