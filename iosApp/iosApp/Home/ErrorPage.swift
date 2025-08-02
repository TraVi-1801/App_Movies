//
//  ErrorPage.swift
//  iosApp
//
//  Created by Nguyễn Trà Vi on 2/8/25.
//

import SwiftUI

struct ErrorPage: View {
    let isOffline: Bool
    let onRetry: () -> Void

    var body: some View {
        VStack(spacing: 16) {
            Spacer().frame(height: 42)

            Image("img_error_page")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(maxWidth: UIScreen.main.bounds.width * 0.35)

            Text(isOffline ? "You aren’t connected to the internet" : "An error occurred please try again")
                .font(.body)
                .fontWeight(.regular)
                .multilineTextAlignment(.center)
                .foregroundColor(.white)

            Text("try again")
                .font(.headline)
                .fontWeight(.medium)
                .foregroundColor(.white)
                .padding(16)
                .background(Color(.darkGray))
                .cornerRadius(16)
                .onTapGesture {
                    onRetry()
                }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .background(Color.clear)
    }
}
