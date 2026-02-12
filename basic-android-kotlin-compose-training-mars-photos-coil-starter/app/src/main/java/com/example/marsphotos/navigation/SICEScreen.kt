package com.example.marsphotos.navigation

import androidx.annotation.StringRes
import com.example.marsphotos.R

enum class SICEScreen(@StringRes val title: Int) {
    LogIn(title = R.string.sicedroid),
    Profile(title = R.string.profile)
}