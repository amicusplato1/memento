package com.example.memento

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color

data class GameUiState(
    val gameStarted: Boolean = false,
    val score: Int = 0,
    val level: Int = 1,
    val levelDisplay: Boolean = false,
    val amountOfElements: Int = 9,
    val levelSequence: List<Int> = List(level) { (0 until amountOfElements).random() },
    val highlightedElements: SnapshotStateList<Boolean> = List(amountOfElements) { false }.toMutableStateList(),
    val highlightTime: Long = 800L,
    val highlightColor: Color = Color.Cyan,
    val elementColor: Color = Color.LightGray,
    val correctElementSequenceIndex: Int = 0,
    val correctElement: Int = levelSequence[correctElementSequenceIndex],
)
