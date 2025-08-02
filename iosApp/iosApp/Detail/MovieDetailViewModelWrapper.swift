//
//  MovieDetailViewModelWrapper.swift
//  iosApp
//
//  Created by Nguyen TraVi on 8/1/25.
//
import Combine
import Shared

class MovieDetailViewModelWrapper: ObservableObject {
    @Published var viewModel: MovieDetailViewModel!

    @Published var isLoading: Bool = false

    init(movieId: Int) {
        self.viewModel = MovieDetailViewModel(movieId: movieId)
    }
}
