//
//  ItemMovie.swift
//  iosApp
//
//  Created by Nguyễn Trà Vi on 2/8/25.
//

import SwiftUI
import Shared

struct MovieItemView: View {
    let item: Movie

    var body: some View {
        ZStack(alignment: .bottomLeading) {
            
            AsyncImage(url: URL(string: item.posterUrl ?? "-")){image in
                image.resizable().aspectRatio(2/3, contentMode: .fill)
                    .frame(height: 240)
            }placeholder: {
                Color.gray.aspectRatio(2/3, contentMode: .fill)
                    .frame(height: 240)
            }.aspectRatio(2/3, contentMode: .fill)
                .frame(height: 240)
                .cornerRadius(12)
                .overlay(
                    Image(systemName: "play.circle.fill")
                        .resizable()
                        .frame(width: 42, height: 42)
                        .foregroundColor(.white.opacity(0.6))
                        .clipShape(Circle()),
                    alignment: .center
                )
            
            
            VStack(alignment: HorizontalAlignment.leading, spacing: 8) {
                Text(item.title ?? "-")
                    .font(.title3)
                    .bold()
                    .foregroundColor(.white)
                    .lineLimit(2)
                HStack (alignment: VerticalAlignment.center,){
                    Text("(\(item.rating ?? "")/\(item.vote ?? ""))")
                        .font(.caption)
                        .foregroundColor(.white)
                    Spacer()
                    Text(item.releaseDate ?? "-")
                        .font(.caption)
                        .foregroundColor(.white)
                }
                
            }
            .frame(maxWidth: .infinity)
            .padding(6)
            .background(Color.black.opacity(0.4))
            .cornerRadius(12, corners: [.bottomLeft, .bottomRight])
        }
        .background(Color.black)
        .cornerRadius(12, corners: [.bottomLeft, .bottomRight])
        .shadow(radius: 4)
    }
}



struct RoundedCorner: Shape {
    var radius: CGFloat
    var corners: UIRectCorner

    func path(in rect: CGRect) -> Path {
        let path = UIBezierPath(
            roundedRect: rect,
            byRoundingCorners: corners,
            cornerRadii: CGSize(width: radius, height: radius)
        )
        return Path(path.cgPath)
    }
}


extension View {
    func cornerRadius(_ radius: CGFloat, corners: UIRectCorner) -> some View {
        clipShape(RoundedCorner(radius: radius, corners: corners))
    }
}
