package yapp.be.apiapplication.auth.controller.model

import yapp.be.apiapplication.auth.service.model.SignUpShelterWithEssentialInfoRequestDto
import yapp.be.model.Address
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

enum class ShelterSignUpCheckDuplicationType {
    EMAIL, NAME
}
