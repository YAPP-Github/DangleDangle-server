package yapp.be.storage.jpa.model

import jakarta.persistence.Embeddable


@Embeddable
data class Address(
    private val address: String,
    private val addressDetail: String,
    private val postalCode: String,
    private val latitude: Double,
    private val longitude: Double,
)
