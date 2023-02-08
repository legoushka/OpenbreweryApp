package ru.legoushka.openbreweryapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ru.legoushka.openbreweryapp.R
import ru.legoushka.openbreweryapp.domain.entity.Brewery
import ru.legoushka.openbreweryapp.domain.entity.BreweryType
import ru.legoushka.openbreweryapp.presentation.theme.DefaultTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreweryCard(
    brewery: Brewery,
    isFavorite: Boolean,
    onClick: (Brewery) -> Unit,
    onFavorite: (Brewery) -> Unit,
    modifier: Modifier = Modifier,
) {
    val paddingSmall = dimensionResource(R.dimen.padding_small)
    val paddingMedium = dimensionResource(R.dimen.padding_medium)
    val address = listOfNotNull(
        brewery.street,
        brewery.city,
        brewery.state,
        brewery.country
    ).joinToString(", ")

    Card(
        onClick = { onClick(brewery) },
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = brewery.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, false)
                )
                IconButton(
                    onClick = {onFavorite(brewery)},
                )
                {
                    Icon(
                        imageVector = if (isFavorite) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = stringResource(
                            R.string.favorite_icon_button
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(paddingSmall))

            Text(
                text = address,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(paddingSmall))

            BreweryTypeText(
                breweryType = brewery.breweryType,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(paddingSmall))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewBreweryCardLongName() {
    DefaultTheme {
        BreweryCard(
            brewery = Brewery(
                id = "madtree-brewing-cincinnati",
                name = "MadTree Brewingaaaaaaaaaaaaaaaaaaaaaaa",
                breweryType = BreweryType.REGIONAL,
                street = "3301 Madison Rd",
                city = "Cincinnati",
                state = "Ohio",
                country = "United States",
                longitude = "-84.4239715",
                latitude = "39.1563725",
                phone = "5138368733",
                websiteUrl = "http://www.madtreebrewing.com",

            ),
            isFavorite = true,
            onClick = {},
            onFavorite = {}
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewBreweryCard() {
    DefaultTheme {
        BreweryCard(
            brewery = Brewery(
                id = "madtree-brewing-cincinnati",
                name = "MadTree Brewing",
                breweryType = BreweryType.REGIONAL,
                street = "3301 Madison Rd",
                city = "Cincinnati",
                state = "Ohio",
                country = "United States",
                longitude = "-84.4239715",
                latitude = "39.1563725",
                phone = "5138368733",
                websiteUrl = "http://www.madtreebrewing.com",
            ),
            onClick = {},
            onFavorite = {},
            isFavorite = false
        )
    }
}