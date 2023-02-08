package ru.legoushka.openbreweryapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.legoushka.openbreweryapp.R
import ru.legoushka.openbreweryapp.presentation.theme.DefaultTheme


@Composable
fun PaginationBar(
    currentPage: Int,
    pagesTotal: Int,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        PaginationButton(
            onPageChange = onPreviousPage,
            title = stringResource(R.string.previous_page),
            enabled = currentPage > 1,
        )
        Text(text = "$currentPage")
        PaginationButton(
            onPageChange = onNextPage,
            title = stringResource(R.string.next_page),
            enabled = currentPage < pagesTotal
        )
    }
}

@Composable
fun PaginationButton(
    onPageChange: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean
) {
    FilledTonalButton(onClick = onPageChange, enabled = enabled, modifier = modifier.width(125.dp)) {
        Text(text = title)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPaginationBar3() {
    DefaultTheme() {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            PaginationBar(
                currentPage = 3,
                onNextPage = { },
                onPreviousPage = { },
                pagesTotal = 3
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPaginationBar1() {
    DefaultTheme() {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            PaginationBar(
                currentPage = 1,
                onNextPage = { },
                onPreviousPage = { },
                pagesTotal = 4
            )
        }
    }
}