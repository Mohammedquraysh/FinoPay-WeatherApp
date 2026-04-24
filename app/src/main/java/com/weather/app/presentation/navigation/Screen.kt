package com.weather.app.presentation.navigation

object NavArgs {
    const val CITY_ID = "cityId"
}

sealed class Screen(val route: String) {
    data object CityList : Screen("city_list")
    data object CityDetail : Screen("city_detail/{${NavArgs.CITY_ID}}") {
        fun createRoute(cityId: Long) = "city_detail/$cityId"
    }
}
