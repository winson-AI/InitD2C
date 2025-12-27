package com.example.initd2c

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform