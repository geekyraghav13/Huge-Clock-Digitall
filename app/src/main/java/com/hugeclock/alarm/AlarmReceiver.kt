package com.hugeclock.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hugeclock.ui.AlarmActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // When the alarm fires, create an intent to start our AlarmActivity
        val i = Intent(context, AlarmActivity::class.java).apply {
            // We need this flag to start an activity from a background service
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(i)
    }
}