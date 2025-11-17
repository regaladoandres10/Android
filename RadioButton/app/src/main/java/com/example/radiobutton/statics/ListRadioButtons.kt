package com.example.radiobutton.statics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.radiobutton.models.ToggleableInfo

val radioButtons = listOf(
    ToggleableInfo(
        isChecked = false,
        text = "Photos"
    ),
    ToggleableInfo(
        isChecked = false,
        text = "Videos"
    ),
    ToggleableInfo(
        isChecked = false,
        text = "Audio"
    )
)

