# CurrencyConverter
CurrencyConverter is an Android application for converting currency rates using MVVM architecture, Room database, ViewModels, and test cases. This app provides offline currency rates for seamless conversions even without an active internet connection.

## Features
- **Currency Conversion**: Convert between different currencies using the latest available exchange rates.

- **Offline Mode**: Automatically switches to offline mode when the internet is not available, allowing for currency conversions using cached data.
- **MVVM Architecture**: The app follows the MVVM (Model-View-ViewModel) architecture to maintain a clean separation between the UI and the underlying data layers.
- **Room Database**: Used for local storage of currency exchange rates to enable offline access and persistence.
- **LiveData**: Ensures efficient and responsive UI updates whenever the currency data changes.
- **Hilt Dependency Injection**: Hilt is used for dependency injection to simplify the app’s architecture.
- **Unit Testing**: Includes unit tests to ensure the functionality of ViewModels, repositories, and other components.

## Tech Stack
- **Kotlin**: All code is written in Kotlin, ensuring modern and efficient app development.
- 
- **Room Database**: For local storage of currency exchange rates, enabling offline capabilities.
- **MVVM Architecture**: Separation of concerns between data and UI logic.
- **LiveData & ViewModel**: For reactive UI updates based on data changes.
- **Hilt**: For dependency injection, ensuring modular and testable code.
- **Coroutines**: For handling background tasks such as fetching currency data.

## Installation
Clone the repository: git clone https://github.com/suyashm002/currencyConverter.git
Open the project in Android Studio.
Sync the project with Gradle files.
Run the app on an emulator or a physical device.

## Usage
* The app will fetch currency rates when online and cache them for offline use.
* You can convert currencies by selecting a base currency and entering an amount.
* The conversion result will show the equivalent amounts in various other currencies.
