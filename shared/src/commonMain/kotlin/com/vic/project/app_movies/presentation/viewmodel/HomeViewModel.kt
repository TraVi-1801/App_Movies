package com.vic.project.app_movies.presentation.viewmodel

import com.vic.project.app_movies.domain.model.Movie
import com.vic.project.app_movies.domain.usecase.GetTrendingMoviesUseCase
import com.vic.project.app_movies.domain.usecase.SearchMoviesUseCase
import com.vic.project.app_movies.utils.NativeCoroutinesState
import com.vic.project.app_movies.utils.ResultWrapper
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update

class HomeViewModel internal constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : KMMViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    @NativeCoroutinesState
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        loadTrending()
        observeSearchQuery()
    }

    private fun loadTrending() {
        launchOnIO {
            getTrendingMoviesUseCase().collectResult()
        }
    }

    fun updateInputSearch(query: String) {
        searchQuery.value = query
        _uiState.update { it.copy(search = query) }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        launchOnIO {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank()) {
                        loadTrending()
                    } else {
                        searchMovies(query)
                    }
                }
        }
    }

    private suspend fun searchMovies(query: String) {
        searchMoviesUseCase(query).collectResult()
    }

    fun retry() {
        val currentSearch = searchQuery.value
        launchOnIO {
            if (currentSearch.isNotBlank()) {
                searchMovies(currentSearch)
            } else {
                loadTrending()
            }
        }
    }


    private suspend fun Flow<ResultWrapper<List<Movie>>>.collectResult() {
        collect { result ->
            when (result) {
                is ResultWrapper.Loading -> {
                    _uiState.update { it.copy(isLoading = true, isError = false) }
                }

                is ResultWrapper.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listData = result.data,
                            isError = false
                        )
                    }
                }

                is ResultWrapper.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isError = true
                        )
                    }
                }
            }
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val listData: List<Movie> = emptyList(),
    val search: String = "",
)