package com.vic.project.app_movies

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform