package yapp.be.apiapplication.shelter.service.model

import yapp.be.domain.model.Address

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
