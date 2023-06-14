package yapp.be.apiapplication.shelter.controller.model

import yapp.be.apiapplication.shelter.service.model.SignUpWithAdditionalInfoRequestDto
import yapp.be.enum.OutLinkType

data class SignUpWithAdditionalInfoRequest(
    val outLinks: List<ShelterSignUpOutLinkInfo>,
    val parkingInfo: ShelterSignUpParkingInfo,
    val donation: ShelterSignUpDonationInfo,
    val notification: String?,
) {
    fun toDto(): SignUpWithAdditionalInfoRequestDto {
        return SignUpWithAdditionalInfoRequestDto()
    }
}

data class ShelterSignUpParkingInfo(
    val isParkingEnabled: Boolean,
    val parkingNotifications: String?,
)
data class ShelterSignUpDonationInfo(
    val account: String,
    val bankName: String
)
data class ShelterSignUpOutLinkInfo(
    val outLinkType: OutLinkType,
    val url: String
)
