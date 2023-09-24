package yapp.be.apiapplication.shelter.controller.model

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.hibernate.validator.constraints.Length
import yapp.be.apiapplication.auth.controller.model.ShelterSignUpAddressInfo
import yapp.be.apiapplication.shelter.service.model.EditShelterProfileImageRequestDto
import yapp.be.apiapplication.shelter.service.model.EditShelterWithAdditionalInfoRequestDto
import yapp.be.apiapplication.shelter.service.model.EditWithEssentialInfoRequestDto
import yapp.be.model.vo.Address
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.model.enums.volunteerActivity.OutLinkType

data class EditShelterAlarmEnabledRequest(
    @field:NotNull(message = "값이 비어있습니다.")
    val alarmEnabled: Boolean
)

data class EditShelterProfileImageRequest(
    @field:NotBlank(message = "값이 비어있습니다.")
    val url: String
) {
    fun toDto(): EditShelterProfileImageRequestDto {
        return EditShelterProfileImageRequestDto(
            profileImageUrl = url
        )
    }
}

data class EditShelterEssentialInfoRequest(
    @field:NotBlank(message = "값이 비어있습니다.")
    val name: String,
    @field:Pattern(
        regexp = "^\\d{2,3}\\d{3,4}\\d{4}\$",
        message = "올바른 전화번호 형식인지 확인해주세요. (- 제외 필요)"
    )
    val phoneNumber: String,
    @field:Length(max = 300, message = "입력 가능 글자수를 초과했습니다.")
    @field:NotBlank(message = "값이 비어있습니다.")
    val description: String,
    @field:Valid
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
    val parkingInfo: EditShelterParkingInfo?,
    val bankAccount: EditShelterDonationInfo?,
    val notice: String?,
) {
    fun toDto(): EditShelterWithAdditionalInfoRequestDto {
        return EditShelterWithAdditionalInfoRequestDto(
            outLinks = this.outLinks.map {
                Pair(it.outLinkType, it.url)
            },
            bankAccount = bankAccount?.let {
                BankAccount(
                    name = this.bankAccount.bankName,
                    accountNumber = this.bankAccount.accountNumber,
                )
            },
            parkingInfo = parkingInfo?.let {
                ShelterParkingInfo(
                    parkingEnabled = this.parkingInfo.parkingEnabled,
                    parkingNotice = this.parkingInfo.parkingNotice
                )
            },
            notice = this.notice
        )
    }
    data class EditShelterParkingInfo(
        val parkingEnabled: Boolean,
        val parkingNotice: String,
    )
    data class EditShelterDonationInfo(
        val accountNumber: String,
        val bankName: String
    )
    data class EditShelterOutLinkInfo(
        @field:NotBlank(message = "값이 비어있습니다.")
        val outLinkType: OutLinkType,
        val url: String
    )
}
