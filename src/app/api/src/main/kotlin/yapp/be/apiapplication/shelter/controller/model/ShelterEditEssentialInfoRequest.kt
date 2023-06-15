package yapp.be.apiapplication.shelter.controller.model

data class ShelterEditEssentialInfoRequest(
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: ShelterSignUpAddressInfo,
)
