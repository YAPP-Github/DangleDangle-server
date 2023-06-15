package yapp.be.apiapplication.shelter.controller.model

import yapp.be.apiapplication.shelter.service.model.EditProfileImageRequestDto
import yapp.be.apiapplication.shelter.service.model.EditWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.EditWithEssentialInfoRequestDto
import yapp.be.domain.model.Address
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.enum.OutLinkType

data class ShelterEditProfileImageRequest(
    val url: String
) {
    fun toDto(shelterId: Long): EditProfileImageRequestDto {
        return EditProfileImageRequestDto(
            shelterId = shelterId,
            profileImageUrl = url
        )
    }
}

data class ShelterEditEssentialInfoRequest(
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: ShelterSignUpAddressInfo,

) {
    fun toDto(): EditWithEssentialInfoRequestDto {
        return EditWithEssentialInfoRequestDto(
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

data class ShelterEditAdditionalInfoRequest(
    val outLinks: List<ShelterEditOutLinkInfo>,
    val parkingInfo: ShelterEditParkingInfo,
    val donation: ShelterEditDonationInfo,
    val notice: String?,
) {
    fun toDto(shelterId: Long): EditWithAdditionalInfoRequestDto {
        return EditWithAdditionalInfoRequestDto(
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
    data class ShelterEditParkingInfo(
        val isParkingEnabled: Boolean,
        val parkingNotice: String?,
    )
    data class ShelterEditDonationInfo(
        val accountNumber: String,
        val bankName: String
    )
    data class ShelterEditOutLinkInfo(
        val outLinkType: OutLinkType,
        val url: String
    )
}
