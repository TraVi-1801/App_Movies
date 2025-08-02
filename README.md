# 🎬 App Movies - Kotlin Multiplatform (KMP)

A cross-platform Movies app built using **Kotlin Multiplatform Mobile (KMM)**. The app allows users to discover trending movies, view movie details, and cache data for offline usage. It is designed with modern UI and clean architecture principles.

- 💻 Android: Jetpack Compose  
- 📱 iOS: SwiftUI  
- 🧠 Shared Logic: Kotlin Multiplatform (KMP)

---

## 📸 Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/ad273182-977d-47a3-9d8f-fad5cdc5dada" alt="Android Demo" width="20%"/>
  <img src="https://github.com/user-attachments/assets/7529d41c-0db8-402a-8e85-bfcc69fdb92a" alt="iOS Demo" width="20%" />
</p>


---

## 🛠 Tech Stack

| Layer          | Technology                        |
|----------------|------------------------------------|
| UI (Android)   | [Jetpack Compose](https://developer.android.com/jetpack/compose) |
| UI (iOS)       | [SwiftUI](https://developer.apple.com/xcode/swiftui/) |
| Shared Logic   | Kotlin Multiplatform (KMP)         |
| Dependency Injection | [Koin](https://insert-koin.io/)               |
| API Client     | [Ktor](https://ktor.io/)           |
| Database       | [SQLDelight](https://cashapp.github.io/sqldelight/) |
| Logger         | Custom KMP Logger                  |

---

## 🌐 Features

- 🔥 Display trending movies (daily/weekly).
- 🎥 View detailed information about each movie.
- 🚀 Optimized offline experience using **firstOffline** strategy.
  - When app is launched without network, cached content is displayed first.
- 📅 Auto-refresh of trending data when a **new day** is detected.
- 📲 Unified UI for Android and iOS with shared business logic.
- 📁 SQLDelight caching for offline and persistent data.
- 💉 Koin for dependency injection across platforms.
- 🌐 Ktor for robust and scalable network layer.
- 🧩 Modular and testable architecture.

---

## 🪵 Custom Logging

A shared `Logger` is used in both platforms to support:

- 🌍 API Logging: show `cURL` format for network requests (useful for debugging).
- 📦 Local Caching Logs: show `GET`, `WRITE`, and `CACHE` operations for SQLDelight in development.

This gives better insight into both API and local database behavior during development.

---

## 🏗 Architecture

- **Presentation Layer**
  - Android: Jetpack Compose
  - iOS: SwiftUI
  - ViewModels shared via KMP + `StateFlow`

- **Domain Layer**
  - UseCases and business logic

- **Data Layer**
  - Repositories
  - Remote: Ktor API service
  - Local: SQLDelight cache

- **DI Layer**
  - Koin modules for shared and platform-specific injection

---

## 🚧 Setup & Build

### Prerequisites

- Android Studio (KMP plugin installed)
- Xcode (for iOS)
- Kotlin Multiplatform Mobile plugin
- Git & GitHub CLI

### Clone Repo

```bash
git clone https://github.com/TraVi-1801/App_Movies.git
