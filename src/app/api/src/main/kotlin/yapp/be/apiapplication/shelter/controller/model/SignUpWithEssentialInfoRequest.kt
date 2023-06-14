package yapp.be.apiapplication.shelter.controller.model

import yapp.be.apiapplication.shelter.service.model.SignUpWithEssentialInfoRequestDto

data class SignUpWithEssentialInfoRequest(
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: ShelterSignUpAddressInfo,
) {
    fun toDto(): SignUpWithEssentialInfoRequestDto {
        return SignUpWithEssentialInfoRequestDto(
            email = email,
            password = password,
            name = name,
            phoneNumber = phoneNumber,
            description = description,
            addressInfo = address
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
