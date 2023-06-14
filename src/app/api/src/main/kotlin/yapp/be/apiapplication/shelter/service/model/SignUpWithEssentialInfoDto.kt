package yapp.be.apiapplication.shelter.service.model

import yapp.be.apiapplication.shelter.controller.model.ShelterSignUpAddressInfo
import yapp.be.domain.model.Address
import yapp.be.model.Email

class SignUpWithEssentialInfoRequestDto(
    email: String,
    val password: String,
    val name: String,
    val phoneNumber: String,
    val description: String,
    addressInfo: ShelterSignUpAddressInfo
) {

    val email = Email(email)
    val address = Address(
        address = addressInfo.address,
        addressDetail = addressInfo.addressDetail,
        postalCode = addressInfo.postalCode,
        latitude = addressInfo.latitude,
        longitude = addressInfo.longitude
    )
}
class SignUpWithEssentialInfoResponseDto(
    val shelterId: Long,
    val shelterUserId: Long
)
