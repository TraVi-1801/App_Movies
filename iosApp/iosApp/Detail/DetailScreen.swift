//
//  DetailScreen.swift
//  iosApp
//
//  Created by Nguyen TraVi on 8/1/25.
import Foundation
import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct DetailScreen: View {
    let objectId: Int32
    
    @StateViewModel var viewModel: Shared.MovieDetailViewModel = KoinIOS.companion.shared.movieDetailViewModel
    
    var body: some View {
        
        ScrollView{
            
            if viewModel.uiState.isLoading || viewModel.uiState.errorMessage{
                DetailLoading(isError: viewModel.uiState.errorMessage) {
                    viewModel.retry()
                }
            } else {
                
                
                VStack{
                    
                    BannerDetail(movie: viewModel.uiState.movie)
                    
                    VoteAndReleaseDate(movie: viewModel.uiState.movie)
                    
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Overview")
                            .font(.headline)
                            .foregroundColor(.white)
                        
                        Text(viewModel.uiState.movie?.overview ?? "-")
                            .font(.subheadline)
                            .foregroundColor(.white)
                            .fixedSize(horizontal: false, vertical: true)
                    }
                    .frame(maxWidth: .infinity)
                    .padding(.horizontal, 16)
                    
                    Spacer().frame(height: 16)
                    
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                
            }
            
        }
        .onAppear {
            viewModel.loadMovie(movieId: objectId)
        }
        
    }
}



// Vote & Release Date
struct VoteAndReleaseDate: View {
    let movie: MovieDetail?
    
    var body: some View {
        // Vote & Release Date
        VStack(alignment: .leading ){
            HStack {
                Text("(\(movie?.vote_average ?? "-")/\(movie?.vote_count ?? "-"))")
                    .font(.subheadline)
                    .foregroundColor(.white)
                
                Spacer()
                Text("Release date: \(movie?.releaseDate ?? "")")
                    .font(.subheadline)
                    .fontWeight(.medium)
                    .foregroundColor(.white)
            }
            
            // Runtime
            HStack {
                Text("Runtime: \(movie?.runtime)")
                    .font(.subheadline)
                    .foregroundColor(.white)
                Spacer()
            }
            
            // Genres
            HStack {
                Text("Genres: \(movie?.genres ?? "-")")
                    .font(.subheadline)
                    .foregroundColor(.white)
            }
        }
        .padding(.horizontal, 16)
    }
    
}
