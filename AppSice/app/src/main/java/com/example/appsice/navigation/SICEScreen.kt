package com.example.appsice.navigation

import androidx.annotation.StringRes
import com.example.appsice.R

enum class SICEScreen(@StringRes val title: Int) {
    LogIn(title = R.string.sicedroid),
    Profile(title = R.string.profile)
}