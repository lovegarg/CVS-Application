# Rick & Morty Characters – Android (Jetpack Compose)
A simple Android app that lists Rick & Morty characters using the public Rick and Morty API.
Built with modern Android tools: Kotlin, Jetpack Compose, MVVM, Clean Architecture, Hilt, Retrofit, and Coroutines.

Features:
	•	Character list screen with:
	•	Search by name (debounced to avoid extra API calls)
	•	Loading, empty, and error states
	•	Character detail screen with:
	•	Large character image
	•	Status, species, origin, location, episode count
	•	Type‑safe navigation between list and detail screens
	•	Basic unit tests for ViewModels
  
Tech Stack:
	•	Language: Kotlin
	•	UI: Jetpack Compose, Material 3
	•	Architecture: MVVM + Clean Architecture (domain, data, presentation)
	•	DI: Hilt
	•	Networking: Retrofit + OkHttp + Gson
	•	Images: Coil ( AsyncImage )
	•	Coroutines: Kotlin Coroutines + StateFlow
  •	Testing: JUnit4, MockK, Coroutines Test
