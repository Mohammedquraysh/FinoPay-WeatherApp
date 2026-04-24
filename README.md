# WeatherNow 

A production-grade Android weather application built with Kotlin, Jetpack Compose, and Clean Architecture + MVVM.

---

## 📐 Architecture

```
WeatherNow
domain/          # Pure Kotlin — models, repository interface, use cases
data/            # Retrofit (remote) + Room (local) + repository impl
 presentation/    # Compose screens, ViewModels, navigation, theme
 core/            # DI modules, NetworkMonitor, utilities
 worker/          # WorkManager — hourly notification
```

### Layer responsibilities

| Layer | Responsibility |
| **Domain** | Business logic. No Android imports. Pure Kotlin data classes, repository interface, use cases. |
| **Data** | Implements `WeatherRepository`. Fetches from OpenWeatherMap via Retrofit, persists to Room. Cache-first strategy. |
| **Presentation** | Compose screens + `ViewModel`. One-way data flow with `StateFlow<UiState>`. |
| **Worker** | `HiltWorker` via WorkManager sends top-of-hour notifications for the first favourite city. |

---

---

##Features

- **City list** — 15 curated global cities displaying live temperatures
- **Search** — Real-time debounced filtering (200ms) across city names
- **Favourites** — Tap ⭐ to pin a city to the top of the list; persisted in Room
- **Detail screen** — Full weather breakdown: feels like, humidity, wind, pressure, visibility, sunrise/sunset
- **Hourly notification** — WorkManager fires at the top of every hour showing conditions for your top favourite city (only when app is in background)
- **Offline-first** — Room cache serves stale data immediately; a red banner signals no connectivity
- **Edge-to-edge UI** — Status bar transparent, full immersive gradient backgrounds per weather condition
- **Dark/light theme** — Follows system setting via Material 3

---

##Default Cities

The app preloads 15 cities chosen for global geographic diversity:

| City | Country |
|---|---|
| London | GB |
| New York | US |
| Tokyo | JP |
| Paris | FR |
| Sydney | AU |
| Shanghai | CN |
| Dubai | AE |
| Mumbai | IN |
| São Paulo | BR |
| Cairo | EG |
| Amsterdam | NL |
| Beijing | CN |
| Nairobi | KE |
| Cape Town | ZA |
| Mexico City | MX |

---

## 🔔 Notification Behaviour

- Scheduled via `PeriodicWorkRequestBuilder` with a calculated `initialDelay` to align to the next top-of-hour tick
- Uses `ExistingPeriodicWorkPolicy.UPDATE` — re-scheduling on app start will shift timing to stay accurate
- Notifies for the **first** city in the favourites list
- If no cities are favourited, the worker exits early with `Result.success()` (no notification sent)
- **OEM caveat**: Xiaomi/Huawei/OnePlus aggressively kill background processes. Users may need to whitelist WeatherNow in battery settings. This is a documented Android limitation, not a bug.

---


### Test coverage

| File | What's tested 
 `WeatherRepositoryImplTest` | Cache-first logic, network refresh, offline guard, toggleFavorite delegation, error propagation |
 `CityListViewModelTest` | UiState emissions, search filtering, favourite sorting, toggle delegation, offline state |
 `WeatherUseCasesTest` | All three use cases — delegation, Loading/Error/Success passthrough |
 `WeatherMapperTest` | DTO→Entity→Domain mapping, field accuracy, edge cases (empty weather list) |
 `ExtensionsTest` | Wind direction labels, `millisUntilNextHour` bounds |

---

##  Tech Stack
| Concern | Library |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture + MVVM |
| DI | Hilt |
| Networking | Retrofit 2 + OkHttp 4 + Gson |
| Local DB | Room |
| Async | Coroutines + StateFlow |
| Background | WorkManager |
| Image loading | Coil |
| Navigation | Compose Navigation |
| Testing | MockK + Turbine + Truth + Coroutines Test |

---

## 📁 Project Structure (abbreviated)

```
app/src/main/java/com/weather/app/
Project Structure
🔹 Core

Handles shared utilities, DI, and system-level components

## di/
NetworkModule
DatabaseModule
RepositoryModule
network/
NetworkMonitor.kt

## utils/
Extensions.kt


## Data  
Responsible for data sources (local + remote) and implementations

## local/
WeatherDatabase
WeatherDao
WeatherEntity
## remote/
WeatherApiService
DTOs
repository/
WeatherRepositoryImpl
WeatherMapper
DefaultCities


## Domain
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


## Presentation

## UI layer (Jetpack Compose)

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

