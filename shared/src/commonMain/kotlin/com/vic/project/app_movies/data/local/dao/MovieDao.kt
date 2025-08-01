package com.vic.project.app_movies.data.local.dao

import com.vic.project.app_movies.data.local.db.AppDatabase
import com.vic.project.app_movies.domain.model.Movie

class MovieDao(private val db: AppDatabase) {

    fun getAll(): List<Movie> =
        db.movieQueries.selectAllMovies().executeAsList().map {
            Movie(
                id = it.id.toInt(),
                title = it.title,
                posterUrl = it.posterUrl,
                rating = it.rating,
                vote = it.vote,
                releaseDate = it.releaseDate
            )
        }

    fun insert(movies: List<Movie>) {
        db.movieQueries.transaction {
            movies.forEach {
                db.movieQueries.insertMovie(
                    id = it.id.toLong(),
                    title = it.title,
                    posterUrl = it.posterUrl,
                    rating = it.rating,
                    vote = it.vote,
                    releaseDate = it.releaseDate
                )
            }
        }
    }

    fun clear() {
        db.movieQueries.deleteAllMovies()
    }
}