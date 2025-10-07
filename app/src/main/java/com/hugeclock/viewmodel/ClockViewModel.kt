package com.hugeclock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ClockViewModel : ViewModel() {

    private val _time = MutableStateFlow("")
    val time = _time.asStateFlow()

    private val _date = MutableStateFlow("")
    val date = _date.asStateFlow()

    // NEW: A separate state for the AM/PM indicator
    private val _ampm = MutableStateFlow("")
    val ampm = _ampm.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                // UPDATED: Time formatter no longer includes 'a'
                val timeFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
                _time.value = timeFormat.format(Date())

                val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
                _date.value = dateFormat.format(Date())

                // NEW: A separate formatter just for AM/PM
                val ampmFormat = SimpleDateFormat("a", Locale.getDefault())
                _ampm.value = ampmFormat.format(Date())

                delay(1000L)
            }
        }
    }
}