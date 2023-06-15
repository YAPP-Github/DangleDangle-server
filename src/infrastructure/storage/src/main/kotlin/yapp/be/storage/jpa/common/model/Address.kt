package yapp.be.storage.jpa.common.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    @Column(name = "address")
    val address: String,
    @Column(name = "address_detail")
    val addressDetail: String,
    @Column(name = "postal_code")
    val postalCode: String,
    @Column(name = "latitude")
    val latitude: Double,
    @Column(name = "longitude")
    val longitude: Double,
)
