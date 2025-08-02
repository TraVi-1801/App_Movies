//
//  DetailLoading.swift
//  iosApp
//
//  Created by Nguyễn Trà Vi on 2/8/25.
//

import SwiftUI
import Network

struct DetailLoading: View {
    var isError: Bool
    var onRetry: () -> Void

    @StateObject private var networkMonitor = NetworkMonitor()
    @State private var rotation: Double = 0
    @State private var shimmerOffset: CGFloat = 0

    let shimmerColors = [
        Color.white.opacity(0.6),
        Color.white.opacity(0.2),
        Color.white.opacity(0.6)
    ]

    var body: some View {
        VStack {
            ZStack {
                Rectangle()
                    .fill(Color.gray.opacity(0.5))
                    .frame(maxWidth: .infinity, minHeight: 400)
                    .cornerRadius(8)
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(
                                LinearGradient(
                                    gradient: Gradient(colors: shimmerColors),
                                    startPoint: .topLeading,
                                    endPoint: UnitPoint(x: shimmerOffset, y: shimmerOffset)
                                ),
                                lineWidth: 1.5
                            )
                    )

                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: .white))
                    .scaleEffect(1)
                    .rotationEffect(.degrees(rotation))
                    .frame(width: 32, height: 32)
                    .overlay(
                        Circle()
                            .stroke(
                                AngularGradient(
                                    gradient: Gradient(colors: [Color.blue, Color.purple]),
                                    center: .center
                                ),
                                lineWidth: 2
                            )
                    )
            }

            if isError {
                VStack(spacing: 16) {
                    Text(networkMonitor.isConnected ? "An error occurred, please try again." : "You aren’t connected to the internet")
                        .foregroundColor(.white)
                        .multilineTextAlignment(.center)

                    Text("Try again")
                        .padding(12)
                        .background(Color(.darkGray))
                        .cornerRadius(16)
                        .foregroundColor(.white)
                        .onTapGesture {
                            onRetry()
                        }
                }
                .padding()
            }

            shimmerLine(height: 2, width: 0.4)
            shimmerLine(height: 2, width: 0.3)
            shimmerLine(height: 3, width: 0.45)
            shimmerLine(height: 3, width: 0.45)
            shimmerLine(height: 2, width: 0.77, topPadding: 40)
            shimmerLine(height: 4, width: 0.7)
            shimmerLine(height: 3, width: 0.75)
            shimmerLine(height: 8, width: 0.73)
        }
        .padding()
        .onAppear {
            withAnimation(.linear(duration: 0.36).repeatForever(autoreverses: false)) {
                rotation = 360
            }

            withAnimation(.easeInOut(duration: 1.0).repeatForever(autoreverses: true)) {
                shimmerOffset = 1.0
            }
        }
    }

    @ViewBuilder
    private func shimmerLine(height: CGFloat, width: CGFloat, topPadding: CGFloat = 20) -> some View {
        Rectangle()
            .fill(
                LinearGradient(
                    gradient: Gradient(colors: shimmerColors),
                    startPoint: .topLeading,
                    endPoint: UnitPoint(x: shimmerOffset, y: shimmerOffset)
                )
            )
            .frame(height: height)
            .frame(maxWidth: UIScreen.main.bounds.width * width)
            .cornerRadius(8)
            .padding(.top, topPadding)
    }
}
