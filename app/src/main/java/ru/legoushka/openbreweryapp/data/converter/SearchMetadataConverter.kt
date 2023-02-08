package ru.legoushka.openbreweryapp.data.converter

import ru.legoushka.openbreweryapp.data.model.SearchMetadataModel
import ru.legoushka.openbreweryapp.domain.entity.SearchMetadata
import javax.inject.Inject
import kotlin.math.ceil

class SearchMetadataConverter @Inject constructor() {
    fun convert(from: SearchMetadataModel) : SearchMetadata =
        SearchMetadata(
            pagesTotal = ceil(from.total.toDouble() / from.itemsPerPage.toDouble()).toInt()
        )
}