package com.example.appsicekmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform