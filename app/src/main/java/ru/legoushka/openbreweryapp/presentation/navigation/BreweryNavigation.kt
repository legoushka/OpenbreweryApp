package ru.legoushka.openbreweryapp.presentation.navigation


import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ru.legoushka.openbreweryapp.presentation.navigation.BreweryDestinationArgs.BREWERY_ID_ARG
import ru.legoushka.openbreweryapp.presentation.navigation.BreweryScreens.BREWERIES_SCREEN
import ru.legoushka.openbreweryapp.presentation.navigation.BreweryScreens.BREWERY_DETAIL_SCREEN

private object BreweryScreens {
    const val BREWERIES_SCREEN = "breweries"
    const val BREWERY_DETAIL_SCREEN = "brewery"
}

object BreweryDestinationArgs {
    const val BREWERY_ID_ARG = "breweryId"
}

object BreweryDestinations {
    const val BREWERIES_ROUTE = BREWERIES_SCREEN
    const val BREWERY_DETAIL_ROUTE = "$BREWERY_DETAIL_SCREEN/{$BREWERY_ID_ARG}"
}

class BreweryNavigationActions(private val navController: NavHostController) {
    fun navigateToBreweriesList() {
        navController.navigate(BREWERIES_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id)
        }
    }

    fun navigateToBreweryDetail(breweryId: String) {
        navController.navigate("$BREWERY_DETAIL_SCREEN/$breweryId"){
            launchSingleTop = true
        }
    }
}