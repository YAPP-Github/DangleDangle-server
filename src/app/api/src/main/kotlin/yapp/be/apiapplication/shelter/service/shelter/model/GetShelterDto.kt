package yapp.be.apiapplication.shelter.service.shelter.model

import yapp.be.enums.volunteerevent.OutLinkType

data class GetShelterResponseDto(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: GetShelterAddressInfoDto,
    val profileImageUrl: String?,
    val outLinks: List<GetOutLinkInfoDto>,
    val parkingInfo: GetShelterParkingInfoDto?,
    val bankAccount: GetBankAccountInfoDto?,
    val notice: String?,
)
data class GetShelterAddressInfoDto(
    val address: String,
    val addressDetail: String,
    val postalCode: String,
    val latitude: Double,
    val longitude: Double,
)
data class GetBankAccountInfoDto(
    val bankName: String,
    val accountNumber: String
)
data class GetOutLinkInfoDto(
    val outLinkType: OutLinkType,
    val url: String
)

data class GetShelterParkingInfoDto(
    val parkingEnabled: Boolean,
    val parkingNotice: String?
)
