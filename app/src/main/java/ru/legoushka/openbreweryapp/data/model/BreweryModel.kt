package ru.legoushka.openbreweryapp.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "favorite")
data class BreweryModel(
    @PrimaryKey
    val id: String,
    val name: String,
    @SerialName("brewery_type")
    @ColumnInfo("brewery_type")
    val breweryType: String,
    val street: String?,
    @SerialName("address_2")
    @ColumnInfo("address_2")
    val address2: String?,
    @SerialName("address_3")
    @ColumnInfo("address_3")
    val address3: String?,
    val city: String,
    val state: String?,
    @SerialName("county_province")
    @ColumnInfo("county_province")
    val countryProvince: String?,
    @SerialName("postal_code")
    @ColumnInfo("postal_code")
    val postalCode: String,
    val country: String,
    val longitude: String?,
    val latitude: String?,
    val phone: String?,
    @SerialName("website_url")
    @ColumnInfo("website_url")
    val websiteUrl: String?,
    @SerialName("updated_at")
    @ColumnInfo("updated_at")
    val updatedAt: String,
    @SerialName("created_at")
    @ColumnInfo("created_at")
    val createdAt: String
)