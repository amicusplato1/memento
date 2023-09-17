package com.example.memento

import android.annotation.SuppressLint
import android.app.Application
import android.media.MediaPlayer
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Timer
import kotlin.concurrent.schedule

class GameViewModel(application: Application): AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val mediaPlayer = MyMediaPlayer(context)

    fun startGame() {
        _uiState.update { it.copy(gameStarted = true) }
        startLevel()
    }

    private fun startLevel() {
        // отключает возможность клика по элементам при показе уровня
        _uiState.update { it.copy(levelDisplay = true) }

        var delay = 0L
        for (index in _uiState.value.levelSequence) {
            highlightElement(index = index, delay = delay, sound = mediaPlayer.pullOutSound)
            delay += _uiState.value.highlightTime
        }
        Timer().schedule(delay = delay) {
            _uiState.update { it.copy(levelDisplay = false) }
        }
    }

    private fun highlightElement(index: Int, delay: Long = 0L, sound: MediaPlayer) {
        val lightOffDelay = delay + _uiState.value.highlightTime / 2
        val highlightedElements = _uiState.value.highlightedElements

        Timer().schedule(delay = delay) {
            if(sound.isPlaying) {
                sound.stop()
                sound.prepare()
            }
            sound.start()
            highlightedElements[index] = true
            _uiState.update {
                it.copy(
                    highlightedElements = highlightedElements
                )
            }
        }
        Timer().schedule(delay = lightOffDelay) {
            highlightedElements[index] = false
            _uiState.update {
                it.copy(
                    highlightedElements = highlightedElements
                )
            }
        }
    }

    fun checkElement(beingCheckedElement: Int) {
        if (!_uiState.value.gameStarted || _uiState.value.levelDisplay) return

        if(_uiState.value.correctElement == beingCheckedElement) {
            _uiState.update {
                it.copy(
                    highlightColor = Color.Green
                )
            }
            highlightElement(index = beingCheckedElement, sound = mediaPlayer.lightSound)
            increaseScore()

            if (_uiState.value.correctElementSequenceIndex != _uiState.value.level - 1) {
                val correctElementSequenceIndex = _uiState.value.correctElementSequenceIndex + 1
                _uiState.update {
                    it.copy(
                        correctElementSequenceIndex = correctElementSequenceIndex,
                        correctElement = _uiState.value.levelSequence[correctElementSequenceIndex]
                    )
                }
            } else {
                Timer().schedule(delay = _uiState.value.highlightTime) {
                    val level = _uiState.value.level + 1
                    val levelSequence = List(level) { (0 until _uiState.value.amountOfElements).random() }
                    _uiState.update {
                        it.copy(
                            level = level,
                            levelSequence = levelSequence,
                            correctElementSequenceIndex = 0,
                            correctElement = levelSequence[0]
                        )
                    }
                    _uiState.update {
                        it.copy(highlightColor = Color.Cyan)
                    }
                    startLevel()
                }
            }
        } else {
            _uiState.update {
                it.copy(
                    gameStarted = false,
                    highlightColor = Color.Red
                )
            }
            highlightElement(index = beingCheckedElement, sound = mediaPlayer.glassSound)
            Timer().schedule(delay = _uiState.value.highlightTime) {
                val levelSequence = List(1) { (0 until _uiState.value.amountOfElements).random() }
                _uiState.update {
                    it.copy(
                        level = 1,
                        score = 0,
                        highlightColor = Color.Cyan,
                        levelSequence = levelSequence,
                        correctElementSequenceIndex = 0,
                        correctElement = levelSequence[0]
                    )
                }
            }
        }
    }

    private fun increaseScore() {
        val score = _uiState.value.score + 1
        _uiState.update{
            it.copy(
                score = score
            )
        }
    }
}

