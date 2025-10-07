// Full, final code for MainActivity.kt
package com.hugeclock.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                    AppNavigator()
                }
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val clockViewModel: ClockViewModel = viewModel()

    NavHost(navController = navController, startDestination = "clock") {
        composable("clock") { ClockScreen(navController = navController, viewModel = clockViewModel) }
        // UPDATE THIS LINE: Pass the navController to the SettingsScreen
        composable("settings") { SettingsScreen(navController = navController, viewModel = clockViewModel) }
    }
}

@Composable
fun ClockScreen(navController: NavController, viewModel: ClockViewModel) {
    // Collect the current states from the ViewModel
    val currentTime by viewModel.time.collectAsState(initial = "")
    val currentDate by viewModel.date.collectAsState(initial = "")
    val currentAmPm by viewModel.ampm.collectAsState(initial = "")
    val clockColor by viewModel.clockColor.collectAsState(initial = Color.White)
    val backgroundColor by viewModel.backgroundColor.collectAsState(initial = Color.Black)

    Box(
        modifier = Modifier
            .fillMaxSize()
            // USE THE DYNAMIC BACKGROUND COLOR
            .background(backgroundColor),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = currentTime,
                    // USE THE DYNAMIC CLOCK COLOR
                    color = clockColor,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = currentAmPm,
                    // USE THE DYNAMIC CLOCK COLOR
                    color = clockColor,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }
            Text(
                text = currentDate,
                // USE THE DYNAMIC CLOCK COLOR
                color = clockColor,
                fontSize = 24.sp
            )
        }
        IconButton(
            onClick = { navController.navigate("settings") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                // USE THE DYNAMIC CLOCK COLOR for the icon
                tint = clockColor
            )
        }
    }
}