import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    init() {
        KoinIOS.companion.shared.doInitKoinCross()
    }
    
    var body: some Scene {
        WindowGroup {
            HomeScreen()
        }
    }
}
