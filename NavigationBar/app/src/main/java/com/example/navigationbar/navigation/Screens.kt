package com.example.navigationbar.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import com.example.navigationbar.statics.BottomNavigationItem

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIncon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        hasNews = false,
    ),
    BottomNavigationItem(
        title = "Chat",
        selectedIncon = Icons.Filled.Email,
        unSelectedIcon = Icons.Outlined.Email,
        hasNews = false,
        badgeCount = 45
    ),
    BottomNavigationItem(
        title = "Settings",
        selectedIncon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
        hasNews = true,
    )
)