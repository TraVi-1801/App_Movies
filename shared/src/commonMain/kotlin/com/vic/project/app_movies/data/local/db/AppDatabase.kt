package com.vic.project.app_movies.data.local.db

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun create(): SqlDriver
}

class DatabaseWrapper(driverFactory: DatabaseDriverFactory) {
    val database: AppDatabase by lazy {
        AppDatabase(driverFactory.create())
    }
}