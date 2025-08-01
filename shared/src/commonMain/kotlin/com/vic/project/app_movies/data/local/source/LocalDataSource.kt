package com.vic.project.app_movies.data.local.source

import com.vic.project.app_movies.data.local.dao.MovieCacheMetaDao
import com.vic.project.app_movies.data.local.dao.MovieDao
import com.vic.project.app_movies.data.local.dao.MovieDetailDao
import com.vic.project.app_movies.domain.model.Movie
import com.vic.project.app_movies.domain.model.MovieDetail
import com.vic.project.app_movies.utils.Logger

class LocalDataSource(
    private val movieDao: MovieDao,
    private val detailDao: MovieDetailDao,
    private val cacheMetaDao: MovieCacheMetaDao,
    private val logger: Logger
) {
    fun getTrendingMovies(): List<Movie> {
        logger.d("Cache", "GET: Trending movies from cache")
        return movieDao.getAll()
    }

    fun getTrendingMoviesTimestamp(): Long? {
        logger.d("Cache", "GET: Timestamp Trending movies from cache")
        return cacheMetaDao.getTimestamp()
    }

    fun saveTrendingMovies(timestamp: Long, movies: List<Movie>) {
        movieDao.clear()
        logger.d("Cache", "WRITE: ${movies.size} trending movies to cache")
        logger.d("Cache", "WRITE: $timestamp timestamp to cache")
        movieDao.insert(movies)
        cacheMetaDao.updateTimestamp(timestamp)
    }

    fun getMovieDetail(id: Int): MovieDetail? {
        logger.d("Cache", "GET: Movie ID $id from cache")
        return detailDao.getById(id)
    }

    fun saveMovieDetail(detail: MovieDetail) {
        logger.d("Cache", "WRITE: Movie Detail ID ${detail.id} to cache ")
        detailDao.insert(detail)
    }
}