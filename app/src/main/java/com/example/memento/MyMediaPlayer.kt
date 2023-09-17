package com.example.memento

import android.content.Context
import android.media.MediaPlayer

class MyMediaPlayer(context: Context) {
    val pullOutSound: MediaPlayer = MediaPlayer.create(context, R.raw.pull_out)
    val lightSound: MediaPlayer = MediaPlayer.create(context, R.raw.light)
    val glassSound: MediaPlayer = MediaPlayer.create(context, R.raw.glass)
}