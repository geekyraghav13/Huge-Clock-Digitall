package com.hugeclock.viewmodel

import android.app.Application
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hugeclock.alarm.AlarmScheduler
import com.hugeclock.data.AppSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// We use AndroidViewModel to get access to the application context,
// which is needed for DataStore and the AlarmScheduler.
class ClockViewModel(application: Application) : AndroidViewModel(application) {

    private val appSettings = AppSettings(application)
    private val alarmScheduler = AlarmScheduler(application)

    // --- Time and Date States ---
    private val _time = MutableStateFlow("")
    val time = _time.asStateFlow()

    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    private val _ampm = MutableStateFlow("")
    val ampm = _ampm.asStateFlow()

    // --- Settings States ---
    val is24HourFormat = appSettings.is24HourFormat
    val clockColor = appSettings.clockColor.map { Color(android.graphics.Color.parseColor(it)) }
    val backgroundColor = appSettings.backgroundColor.map { Color(android.graphics.Color.parseColor(it)) }

    init {
        // This is the main loop that updates the clock every second.
        viewModelScope.launch {
            while (true) {
                // Read the current setting value from DataStore.
                val is24Hour = is24HourFormat.first()

                // Choose the correct time format based on the setting.
                val timeFormat = if (is24Hour) {
                    SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                } else {
                    SimpleDateFormat("hh:mm:ss", Locale.getDefault())
                }
                _time.value = timeFormat.format(Date())

                // Format the date string.
                val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
                _date.value = dateFormat.format(Date())

                // Show the AM/PM indicator only if we are in 12-hour format.
                if (is24Hour) {
                    _ampm.value = ""
                } else {
                    val ampmFormat = SimpleDateFormat("a", Locale.getDefault())
                    _ampm.value = ampmFormat.format(Date())
                }

                // Wait for 1 second before the next update.
                delay(1000L)
            }
        }
    }

    // --- Functions to Update Settings ---
    fun set24HourFormat(is24Hour: Boolean) {
        viewModelScope.launch {
            appSettings.set24HourFormat(is24Hour)
        }
    }

    fun setClockColor(color: Color) {
        viewModelScope.launch {
            // Convert the Compose Color object to a hex string to save it.
            val colorString = String.format("#%08X", color.toArgb())
            appSettings.setClockColor(colorString)
        }
    }

    fun setBackgroundColor(color: Color) {
        viewModelScope.launch {
            val colorString = String.format("#%08X", color.toArgb())
            appSettings.setBackgroundColor(colorString)
        }
    }

    // --- Function to Schedule an Alarm ---
    fun scheduleAlarm(hour: Int, minute: Int) {
        alarmScheduler.schedule(hour, minute)
    }
}