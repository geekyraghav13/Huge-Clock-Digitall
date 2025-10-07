package com.hugeclock.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the alarm goes off.
        // For now, we'll just show a simple message to prove it works.
        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_LONG).show()
    }
}