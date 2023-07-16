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

fun Address.toDomainModel(): yapp.be.model.vo.Address {
    return yapp.be.model.vo.Address(
        address = this.address,
        addressDetail = this.addressDetail,
        postalCode = this.postalCode,
        longitude = this.longitude,
        latitude = this.latitude
    )
}
