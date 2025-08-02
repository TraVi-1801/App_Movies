package com.vic.project.app_movies.presentation.viewmodel

import com.vic.project.app_movies.domain.model.MovieDetail
import com.vic.project.app_movies.domain.repository.MovieRepository
import com.vic.project.app_movies.domain.usecase.GetMovieDetailUseCase
import com.vic.project.app_movies.utils.NativeCoroutinesState
import com.vic.project.app_movies.utils.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel internal constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
) : KMMViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    @NativeCoroutinesState
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    private var currentMovieId: Int? = null

    fun loadMovie(movieId: Int) {
        currentMovieId = movieId
        _uiState.update { it.copy(isLoading = true, errorMessage = false) }

        launchOnIO {
            getMovieDetailUseCase.invoke(movieId).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true, errorMessage = false)}
                    }

                    is ResultWrapper.Success -> {
                        _uiState.update {
                            MovieDetailUiState(
                                isLoading = false,
                                movie = result.data,
                                errorMessage = false
                            )
                        }
                    }

                    is ResultWrapper.Error -> {
                        _uiState.update {
                            MovieDetailUiState(
                                isLoading = false,
                                movie = null,
                                errorMessage = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun retry() {
        currentMovieId?.let { loadMovie(it) }
    }
}

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movie: MovieDetail? = null,
    val errorMessage: Boolean = false
)