package com.example.miprimerkmpcompose

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform