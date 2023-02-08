package ru.legoushka.openbreweryapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.legoushka.openbreweryapp.presentation.navigation.BreweryDestinationArgs
import ru.legoushka.openbreweryapp.presentation.navigation.BreweryDestinations
import ru.legoushka.openbreweryapp.presentation.navigation.BreweryNavigationActions
import ru.legoushka.openbreweryapp.presentation.screens.DetailScreen
import ru.legoushka.openbreweryapp.presentation.screens.MainScreen

@Composable
fun BreweryNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = BreweryDestinations.BREWERIES_ROUTE,
    navActions: BreweryNavigationActions = remember(navController) {
        BreweryNavigationActions(navController)
    },
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            BreweryDestinations.BREWERIES_ROUTE,
            arguments = listOf(
                navArgument(BreweryDestinationArgs.BREWERY_ID_ARG) {
                    type = NavType.StringType; defaultValue = ""
                }
            )
        ){ entry ->
            MainScreen(
                onBreweryClick = {brewery -> navActions.navigateToBreweryDetail(brewery.id)}
            )
        }
        composable(BreweryDestinations.BREWERY_DETAIL_ROUTE) {
            DetailScreen(onBack = {navController.popBackStack()} )
        }
    }
}