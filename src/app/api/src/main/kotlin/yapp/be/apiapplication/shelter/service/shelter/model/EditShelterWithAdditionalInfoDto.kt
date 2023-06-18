package yapp.be.apiapplication.shelter.service.shelter.model

import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo

class EditShelterWithAdditionalInfoRequestDto(
    val outLinks: List<ShelterOutLink>,
    val parkingInfo: ShelterParkingInfo,
    val donation: BankAccount,
    val notice: String?,

)
data class EditShelterWithAdditionalInfoResponseDto(
    val shelterId: Long,
    val shelterUserId: Long
)
