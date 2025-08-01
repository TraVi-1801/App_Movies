import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    init() {
        KoinKt.doInitKoin(appModules: [])
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
