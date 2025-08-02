//
//  SearchNewsNotFound.swift
//  iosApp
//
//  Created by Nguyễn Trà Vi on 2/8/25.
//

import SwiftUI

struct SearchNewsNotFound: View {
    let isLoading: Bool
    let isNotFound: Bool

    var body: some View {
        VStack(alignment: .center) {
            if isLoading {
                ProgressView()
            }

            Spacer().frame(height: 32)

            Image("img_error_page")
                .resizable()
                .aspectRatio(1, contentMode: .fit)
                .frame(maxWidth: UIScreen.main.bounds.width * 0.35)

            Spacer().frame(height: 16)

            if isNotFound {
                Text("No movie found")
                    .font(.system(.headline, weight: .semibold))
                    .foregroundColor(.white)
                    .multilineTextAlignment(.center)

                Spacer().frame(height: 8)

                Text("Please search for movie with a different keyword or topic.")
                    .font(.system(.subheadline, weight: .regular))
                    .foregroundColor(.white)
                    .multilineTextAlignment(.center)
            } else {
                Text("There is no news at the moment.")
                    .font(.system(.body, weight: .regular))
                    .foregroundColor(.white)
                    .multilineTextAlignment(.center)
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
    }
}
