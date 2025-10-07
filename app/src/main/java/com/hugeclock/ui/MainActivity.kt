package com.hugeclock.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row // NEW IMPORT
import androidx.compose.foundation.layout.padding // NEW IMPORT
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp // NEW IMPORT
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hugeclock.ui.theme.HugeClockDigitalTheme
import com.hugeclock.viewmodel.ClockViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HugeClockDigitalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ClockScreen()
                }
            }
        }
    }
}

@Composable
fun ClockScreen() {
    val viewModel: ClockViewModel = viewModel()
    val currentTime = viewModel.time.collectAsState().value
    val currentDate = viewModel.date.collectAsState().value
    val currentAmPm = viewModel.ampm.collectAsState().value // NEW: Get the AM/PM state

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // NEW: Use a Row to place time and AM/PM side-by-side
            Row(
                verticalAlignment = Alignment.Bottom // Aligns text along the bottom baseline
            ) {
                // Text for the time
                Text(
                    text = currentTime,
                    color = Color.White,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )
                // Text for AM/PM
                Text(
                    text = currentAmPm,
                    color = Color.White,
                    fontSize = 40.sp, // Smaller font size
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp) // Add some spacing
                )
            }
            // Text for the date
            Text(
                text = currentDate,
                color = Color.White,
                fontSize = 24.sp
            )
        }
    }
}

// ... Preview function remains the same ...