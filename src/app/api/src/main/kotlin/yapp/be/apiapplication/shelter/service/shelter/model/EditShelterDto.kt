package yapp.be.apiapplication.shelter.service.shelter.model

import yapp.be.domain.model.Address
import yapp.be.domain.model.BankAccount
import yapp.be.domain.model.ShelterParkingInfo
import yapp.be.enums.volunteerevent.OutLinkType

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
