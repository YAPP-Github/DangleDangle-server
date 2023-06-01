package yapp.be.domain.model

data class Address (
    val address: String,
    val addressDetail: String,
    val postalCode: String,
    val lat: Double,
    val lng: Double,
)
