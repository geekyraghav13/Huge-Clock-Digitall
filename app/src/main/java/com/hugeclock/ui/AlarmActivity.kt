package com.hugeclock.ui

import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hugeclock.ui.theme.HugeClockDigitalTheme
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : ComponentActivity() {

    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start playing the default alarm sound
        try {
            val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ringtone = RingtoneManager.getRingtone(applicationContext, alarmUri)
            ringtone?.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setContent {
            HugeClockDigitalTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AlarmScreen(
                        onDismiss = {
                            // Stop the sound and close the screen
                            ringtone?.stop()
                            finish()
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Make sure to stop the ringtone if the activity is destroyed
        ringtone?.stop()
    }
}

@Composable
fun AlarmScreen(onDismiss: () -> Unit) {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val currentTime = sdf.format(Date())

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Alarm!", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = currentTime, fontSize = 64.sp)
            Spacer(modifier = Modifier.height(48.dp))
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(text = "Dismiss", fontSize = 24.sp)
            }
        }
    }
}