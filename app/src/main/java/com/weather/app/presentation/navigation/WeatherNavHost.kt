package com.weather.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.weather.app.presentation.citydetail.CityDetailScreen
import com.weather.app.presentation.citylist.CityListScreen

@Composable
fun WeatherNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.CityList.route,
    ) {
        composable(route = Screen.CityList.route) {
            CityListScreen(
                onCityClick = { cityId ->
                    navController.navigate(Screen.CityDetail.createRoute(cityId))
                },
            )
        }

        composable(
            route = Screen.CityDetail.route,
            arguments = listOf(
                navArgument(NavArgs.CITY_ID) { type = NavType.LongType },
            ),
        ) {
            CityDetailScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
