//
//  HomeScreen.swift
//  iosApp
//
//  Created by Nguyễn Trà Vi on 2/8/25.
//
import Foundation
import SwiftUI
import Shared
import KMPObservableViewModelSwiftUI

struct HomeScreen: View {
    
    @StateObject private var networkMonitor = NetworkMonitor()
    
    @StateViewModel private var viewModel: Shared.HomeViewModel = KoinIOS.companion.shared.movieListingViewModel
    
    let gridColumns: [GridItem] = Array(repeating: GridItem(.flexible(), spacing: 16), count: 2)
    
    var searchBinding: Binding<String> {
        Binding<String>(
            get: {
                viewModel.uiState.search
            },
            set: { newValue in
                viewModel.updateInputSearch(query: newValue)
            }
        )
    }
    
    var body: some View {
        NavigationView {
            VStack {
                
                SearchBar(text: searchBinding){
                    viewModel.retry()
                }
                
                if viewModel.uiState.listData.isEmpty {
                    if viewModel.uiState.isError {
                        ErrorPage(
                            isOffline: !networkMonitor.isConnected,
                            onRetry: {
                                viewModel.retry()
                            })
                    } else {
                        SearchNewsNotFound(
                            isLoading: viewModel.uiState.isLoading,
                            isNotFound: !viewModel.uiState.search.isEmpty
                        )
                    }
                } else {
                    
                    ScrollView {
                        LazyVGrid(columns: gridColumns, spacing: 16) {
                            ForEach(viewModel.uiState.listData, id: \.id) { movie in
                                NavigationLink(destination: DetailScreen(objectId: movie.id)){
                                    MovieItemView(item: movie)
                                }
                                .buttonStyle(PlainButtonStyle())
                            }
                        }
                    }
                }
            }
            .padding(.horizontal, 16)
            .background(.black)
        }
    }
}



struct SearchBar: View {
    @Binding var text: String
    var onSearchClicked: () -> Void = {}
    
    var body: some View {
        TextField("Search movie", text: $text)
            .foregroundColor(.black)
            .padding(.horizontal, 12)
            .padding(.trailing, 24) // <-- chừa chỗ cho icon
            .padding(.vertical, 8)
            .background(Color.white)
            .overlay(
                HStack {
                    Spacer()
                    Button(action: {
                        onSearchClicked()
                    }) {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(.gray)
                            .padding(.trailing, 8)
                    }
                }
            )
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(Color.blue, lineWidth: 1)
            )
            .cornerRadius(8)
    }
}
