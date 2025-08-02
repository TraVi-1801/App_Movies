import SwiftUI
import Shared

struct BannerDetail: View {
    let movie: MovieDetail?
    
    var body: some View {
        ZStack(alignment: .bottom) {
            // Background Image
            AsyncImage(url: URL(string: movie?.backdropPath ?? "")) { image in
                image
                    .resizable()
                    .frame(maxWidth: .infinity)
                    .frame(height: 400)
            } placeholder: {
                Color.gray
            }
            .frame(maxWidth: .infinity)
            .frame(height: 400)
            .clipped() 
            
            // Foreground Info: poster + title + overview + button
            HStack(alignment: .center, spacing: 12) {
                
                AsyncImage(url: URL(string: movie?.posterUrl ?? "")) { image in
                    image
                        .resizable()
                        .scaledToFit()
                        .frame(width: 120)
                } placeholder: {
                    Color.gray
                        .frame(width: 120)
                }
                .cornerRadius(8)
                
                VStack(alignment: .leading, spacing: 8) {
                    Text(movie?.title ?? "-")
                        .font(.title2)
                        .fontWeight(.semibold)
                        .lineLimit(2)
                        .foregroundColor(.white)
                    
                    Text(movie?.description_ ?? "-")
                        .font(.subheadline)
                        .lineLimit(2)
                        .fixedSize(horizontal: false, vertical: true)
                        .foregroundColor(.white)
                    
                    if let imdbId = movie?.imdbId, let url = URL(string: imdbId) {
                        Button(action: {
                            UIApplication.shared.open(url)
                        }) {
                            HStack(spacing: 4) {
                                Image(systemName: "play.fill")
                                    .resizable()
                                    .frame(width: 16, height: 16)
                                    .foregroundColor(.white)
                                    .padding(8)
                                    .background(Circle().stroke(Color.white, lineWidth: 1))
                                
                                Text("Watch now")
                                    .font(.caption)
                                    .foregroundColor(.white)
                            }
                            .padding(.horizontal, 12)
                            .padding(.vertical, 8)
                            .background(Color.red)
                            .cornerRadius(8)
                        }
                    }
                }
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.horizontal, 16)

        }
    }
}
