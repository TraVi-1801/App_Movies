package com.vic.project.app_movies.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


object TimeUtils {
    @OptIn(ExperimentalTime::class, ExperimentalTime::class)
    fun Long.isNotToday(): Boolean {
        val savedDate = Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        val currentDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()).date

        return savedDate != currentDate
    }
}