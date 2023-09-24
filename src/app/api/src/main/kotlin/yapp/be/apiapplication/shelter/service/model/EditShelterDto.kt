package yapp.be.apiapplication.shelter.service.model

import yapp.be.model.vo.Address
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.model.enums.volunteerActivity.OutLinkType
data class EditShelterProfileImageRequestDto(
    val profileImageUrl: String
)
data class EditShelterProfileImageResponseDto(
    val shelterId: Long
)

data class EditShelterWithAdditionalInfoRequestDto(
    val outLinks: List<Pair<OutLinkType, String>>,
    val parkingInfo: ShelterParkingInfo?,
    val bankAccount: BankAccount?,
    val notice: String?,

)
data class EditShelterWithAdditionalInfoResponseDto(
    val shelterId: Long,
    val shelterUserId: Long
)

data class EditWithEssentialInfoRequestDto(
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: Address
)

data class EditWithEssentialInfoResponseDto(
    val shelterId: Long,
    val shelterUserId: Long
)

data class EditShelterAlarmEnabledResponseDto(
    val shelterId: Long
)
