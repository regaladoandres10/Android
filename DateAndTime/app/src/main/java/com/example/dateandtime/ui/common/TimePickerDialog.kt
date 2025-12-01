package com.example.dateandtime.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimesPicker() {

//    var selectedTime by remember { mutableStateOf(LocalTime.NOON) }
//    val formattedTime by remember {
//        derivedStateOf {
//            DateTimeFormatter
//                .ofPattern("hh:mm")
//                .format(selectedTime)
//        }
//    }

    //val timeDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }


}
