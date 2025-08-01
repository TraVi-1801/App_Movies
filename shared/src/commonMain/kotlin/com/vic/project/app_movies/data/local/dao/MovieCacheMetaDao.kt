package com.vic.project.app_movies.data.local.dao

import com.vic.project.app_movies.data.local.db.AppDatabase

class MovieCacheMetaDao(private val db: AppDatabase) {
    fun getTimestamp(): Long? =
        db.movieCacheMetaQueries.getCacheTimestamp().executeAsOneOrNull()

    fun updateTimestamp(timestamp: Long) {
        db.movieCacheMetaQueries.insertOrUpdateTimestamp(timestamp)
    }
}