package yapp.be.apiapplication.shelter.controller.model

import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterProfileImageRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditShelterWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.EditWithEssentialInfoRequestDto
import yapp.be.domain.model.Address
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.enum.OutLinkType

data class EditShelterProfileImageRequest(
    val url: String
) {
    fun toDto(shelterId: Long): EditShelterProfileImageRequestDto {
        return EditShelterProfileImageRequestDto(
            shelterId = shelterId,
            profileImageUrl = url
        )
    }
}

data class EditShelterEssentialInfoRequest(
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

data class EditShelterAdditionalInfoRequest(
    val outLinks: List<EditShelterOutLinkInfo>,
    val parkingInfo: EditShelterParkingInfo,
    val donation: EditShelterDonationInfo,
    val notice: String?,
) {
    fun toDto(shelterId: Long): EditShelterWithAdditionalInfoRequestDto {
        return EditShelterWithAdditionalInfoRequestDto(
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
    data class EditShelterParkingInfo(
        val isParkingEnabled: Boolean,
        val parkingNotice: String?,
    )
    data class EditShelterDonationInfo(
        val accountNumber: String,
        val bankName: String
    )
    data class EditShelterOutLinkInfo(
        val outLinkType: OutLinkType,
        val url: String
    )
}
