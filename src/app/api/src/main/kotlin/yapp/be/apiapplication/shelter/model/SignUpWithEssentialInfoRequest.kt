package yapp.be.apiapplication.shelter.model

data class SignUpWithEssentialInfoRequest(
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: ShelterSignUpAddressInfo,

)

data class ShelterSignUpAddressInfo(
    val address: String,
    val addressDetail: String,
    val postalCode: String,
    val latitude: String,
    val longitude: String

)
