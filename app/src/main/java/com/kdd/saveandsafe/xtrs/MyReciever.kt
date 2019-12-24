package com.kdd.saveandsafe.xtrs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings

class MyReciever : BroadcastReceiver() {
    lateinit var mMediaPlayer : MediaPlayer

    override fun onReceive(context: Context?, intent: Intent?) {
        mMediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI)
        mMediaPlayer.start()
    }
}