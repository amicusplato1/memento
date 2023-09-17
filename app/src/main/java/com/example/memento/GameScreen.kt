package com.example.memento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(gameViewModel: GameViewModel = viewModel()) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(60.dp),
        modifier = Modifier.fillMaxSize().offset(y = 150.dp)
    ) {
        Text(
            text = stringResource(R.string.score).uppercase() + ": ${gameUiState.score}",
            fontSize = 36.sp
        )
        GameBoard()
        if (!gameUiState.gameStarted) {
            Button(
                onClick = { gameViewModel.startGame() },
                shape = RoundedCornerShape(10)
            ) {
                Text(
                    text = stringResource(R.string.start).uppercase(),
                    fontSize = 36.sp
                )
            }
        }
    }
}