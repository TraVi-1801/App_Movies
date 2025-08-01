//
//  MovieDetailViewModelWrapper.swift
//  iosApp
//
//  Created by Nguyen TraVi on 8/1/25.
//
import Combine

class MovieDetailViewModelWrapper: ObservableObject {
    private let viewModel: MovieDetailViewModel

    @Published var uiState: MovieDetailUiState = MovieDetailUiState()

    private var cancellable: AnyCancellable?

    init(viewModel: MovieDetailViewModel) {
        self.viewModel = viewModel
        observeState()
    }

    private func observeState() {
        cancellable = viewModel.uiState.watch { [weak self] newState in
            self?.uiState = newState
        }
    }

    func loadMovie(movieId: Int32) {
        viewModel.loadMovie(movieId: movieId)
    }

    func retry() {
        viewModel.retry()
    }
}
