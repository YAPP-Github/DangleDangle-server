package yapp.be.apiapplication.shelter.service.model

import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterOutLink
import yapp.be.domain.model.ShelterParkingInfo

class SignUpWithAdditionalInfoRequestDto(
    val shelterId: Long,
    val shelterUserId: Long,
    val outLinks: List<ShelterOutLink>,
    val parkingInfo: ShelterParkingInfo,
    val donation: BankAccount,
    val notice: String?,

)
data class SignUpWithAdditionalInfoResponseDto(
    val shelterId: Long,
    val shelterUserId: Long
)
