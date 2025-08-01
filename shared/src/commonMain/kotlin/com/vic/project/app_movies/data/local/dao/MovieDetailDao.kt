package com.vic.project.app_movies.data.local.dao

import com.vic.project.app_movies.data.local.db.AppDatabase
import com.vic.project.app_movies.domain.model.MovieDetail

class MovieDetailDao(private val db: AppDatabase) {

    fun getById(id: Int): MovieDetail? {
        return db.movieDetailQueries.selectMovieDetailById(id.toLong()).executeAsOneOrNull()?.let {
            MovieDetail(
                id = it.id.toInt(),
                runtime = it.runtime?.toInt(),
                title = it.title,
                description = it.description,
                overview = it.overview,
                posterUrl = it.posterUrl,
                backdropPath = it.backdropPath,
                imdbId = it.imdbId,
                genres = it.genres,
                vote_average = it.vote_average,
                vote_count = it.vote_count,
                releaseDate = it.releaseDate
            )
        }
    }

    fun insert(detail: MovieDetail) {
        db.movieDetailQueries.insertMovieDetail(
            id = detail.id.toLong(),
            runtime = detail.runtime?.toLong(),
            title = detail.title,
            description = detail.description,
            overview = detail.overview,
            posterUrl = detail.posterUrl,
            backdropPath = detail.backdropPath,
            imdbId = detail.imdbId,
            genres = detail.genres,
            vote_average = detail.vote_average,
            vote_count = detail.vote_count,
            releaseDate = detail.releaseDate
        )
    }
}