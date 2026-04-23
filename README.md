# FinoPay-WeatherApp

📦 Project Structure
**Core**
Handles shared utilities, DI, and system-level components
di/
NetworkModule
DatabaseModule
RepositoryModule
network/
NetworkMonitor.kt
utils/
Extensions.kt

 **Data**
Responsible for data sources (local + remote) and implementations
local/
WeatherDatabase
WeatherDao
WeatherEntity
remote/
WeatherApiService
DTOs
repository/
WeatherRepositoryImpl
WeatherMapper
DefaultCities

 **Domain**
Contains business logic (clean architecture layer)

model/
Weather.kt
Resource.kt
repository/
WeatherRepository (interface)
usecase/
GetCitiesWeatherUseCase
GetCityWeatherUseCase
ToggleFavoriteUseCase
GetFavoriteCitiesUseCase

 **Presentation**
UI layer (Jetpack Compose)
citylist/
CityListScreen
CityListViewModel
citydetail/
CityDetailScreen
CityDetailViewModel
components/
WeatherComponents
CityWeatherCard
navigation/
Screen
WeatherNavHost
theme/
Color
Typography
Theme

 **Worker**
Background tasks
HourlyWeatherWorker.kt

 **Root Files**
MainActivity.kt
WeatherApplication.kt
