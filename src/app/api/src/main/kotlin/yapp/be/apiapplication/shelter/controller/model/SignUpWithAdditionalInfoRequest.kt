package yapp.be.apiapplication.shelter.controller.model

import yapp.be.apiapplication.shelter.service.model.SignUpWithAdditionalInfoRequestDto
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.enum.OutLinkType

data class SignUpWithAdditionalInfoRequest(
    val shelterId: Long,
    val shelterUserId: Long,
    val outLinks: List<ShelterSignUpOutLinkInfo>,
    val parkingInfo: ShelterSignUpParkingInfo,
    val donation: ShelterSignUpDonationInfo,
    val notice: String?,
) {
    fun toDto(): SignUpWithAdditionalInfoRequestDto {
        return SignUpWithAdditionalInfoRequestDto(
            shelterId = this.shelterId,
            shelterUserId = this.shelterUserId,
            outLinks = this.outLinks.map {
                ShelterOutLink(
                    url = it.url,
                    shelterId = shelterId,
                    type = it.outLinkType
                )
            },
            donation = BankAccount(
                name = this.donation.bankName,
                accountNumber = this.donation.accountNumber,
            ),
            parkingInfo = ShelterParkingInfo(
                parkingEnabled = this.parkingInfo.isParkingEnabled,
                notice = this.parkingInfo.parkingNotice

            ),
            notice = this.notice
        )
    }
    data class ShelterSignUpParkingInfo(
        val isParkingEnabled: Boolean,
        val parkingNotice: String?,
    )
    data class ShelterSignUpDonationInfo(
        val accountNumber: String,
        val bankName: String
    )
    data class ShelterSignUpOutLinkInfo(
        val outLinkType: OutLinkType,
        val url: String
    )
}
