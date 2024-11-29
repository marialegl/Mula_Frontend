package com.mula.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
