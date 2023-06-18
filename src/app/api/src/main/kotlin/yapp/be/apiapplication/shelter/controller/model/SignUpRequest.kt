package yapp.be.apiapplication.shelter.controller.model

import yapp.be.apiapplication.shelter.service.shelter.model.SignUpShelterWithEssentialInfoRequestDto
import yapp.be.domain.model.Address
import yapp.be.model.Email

data class SignUpWithEssentialInfoRequest(
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: ShelterSignUpAddressInfo,
) {
    fun toDto(): SignUpShelterWithEssentialInfoRequestDto {
        return SignUpShelterWithEssentialInfoRequestDto(
            email = Email(email),
            password = password,
            name = name,
            phoneNumber = phoneNumber,
            description = description,
            address = Address(
                address = this.address.address,
                addressDetail = this.address.addressDetail,
                postalCode = this.address.postalCode,
                latitude = this.address.latitude,
                longitude = this.address.longitude
            )
        )
    }
}

data class ShelterSignUpAddressInfo(
    val address: String,
    val addressDetail: String,
    val postalCode: String,
    val latitude: Double,
    val longitude: Double

)
