package com.example.dateandtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dateandtime.ui.common.DatePickerDocked
import com.example.dateandtime.ui.common.DatePickerFieldToModal
import com.example.dateandtime.ui.common.TimePickerFieldToModal
import com.example.dateandtime.ui.theme.DateAndTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DateAndTimeTheme {
                TimePickerFieldToModal()
            }
        }
    }
}

