package yapp.be.storage.jpa.common.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    @Column(name = "address")
    val address: String,
    @Column(name = "address_detail")
    val addressDetail: String,
    @Column(name = "postal_code", nullable = false)
    val postalCode: String,
    @Column(name = "latitude", nullable = false)
    val latitude: Double,
    @Column(name = "longitude", nullable = false)
    val longitude: Double,
)
