package com.hugeclock.ui

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hugeclock.viewmodel.ClockViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: ClockViewModel) {
    val is24Hour by viewModel.is24HourFormat.collectAsState(initial = false)
    val currentClockColor by viewModel.clockColor.collectAsState(initial = Color.White)
    val currentBackgroundColor by viewModel.backgroundColor.collectAsState(initial = Color.Black)
    val context = LocalContext.current

    val colorOptions = listOf(
        Color.White, Color.Black, Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // --- 24-Hour Format Setting ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Use 24-hour format", fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = is24Hour,
                    onCheckedChange = { viewModel.set24HourFormat(it) }
                )
            }
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // --- Clock Color Setting ---
            Text(text = "Clock Color", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ColorPickerRow(
                colors = colorOptions,
                selectedColor = currentClockColor,
                onColorSelected = { viewModel.setClockColor(it) }
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // --- Background Color Setting ---
            Text(text = "Background Color", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ColorPickerRow(
                colors = colorOptions,
                selectedColor = currentBackgroundColor,
                onColorSelected = { viewModel.setBackgroundColor(it) }
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // --- Alarm Button ---
            Button(
                onClick = {
                    // Show the Time Picker Dialog
                    val calendar = Calendar.getInstance()
                    val timePickerDialog = TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            // When a time is selected, schedule the alarm
                            viewModel.scheduleAlarm(hourOfDay, minute)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        is24Hour // The dialog will be 12 or 24 hour based on user's preference
                    )
                    timePickerDialog.show()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Set An Alarm", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ColorPickerRow(
    colors: List<Color>,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(colors) { color ->
            val isSelected = color == selectedColor
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = if (isSelected) 2.dp else 0.dp,
                        color = if (isSelected) Color.Gray else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onColorSelected(color) }
            )
        }
    }
}