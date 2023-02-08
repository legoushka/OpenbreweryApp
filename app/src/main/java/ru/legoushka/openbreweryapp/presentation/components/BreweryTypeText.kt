package ru.legoushka.openbreweryapp.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import ru.legoushka.openbreweryapp.R
import ru.legoushka.openbreweryapp.domain.entity.BreweryType

@Composable
fun BreweryTypeText(
    breweryType: BreweryType,
    style: TextStyle,
    modifier: Modifier = Modifier
){
    val convertedBreweryType: Int =
        when (breweryType) {
            BreweryType.MICRO -> R.string.brewery_micro
            BreweryType.NANO -> R.string.brewery_nano
            BreweryType.REGIONAL -> R.string.brewery_regional
            BreweryType.BREWPUB -> R.string.brewery_brewpub
            BreweryType.LARGE -> R.string.brewery_large
            BreweryType.PLANNING -> R.string.brewery_planning
            BreweryType.BAR -> R.string.brewery_bar
            BreweryType.CONTRACT -> R.string.brewery_contract
            BreweryType.PROPRIETOR -> R.string.brewery_proprietor
            BreweryType.CLOSED -> R.string.brewery_closed
        }

    Text(text = stringResource(convertedBreweryType), style = style, modifier = modifier)
}