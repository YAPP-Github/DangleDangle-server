package yapp.be.apiapplication.shelter.service.model

import yapp.be.domain.model.Address
import yapp.be.model.Email

class SignUpWithEssentialInfoRequestDto(
    val email: Email,
    val password: String,
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: Address
)
data class SignUpWithEssentialInfoResponseDto(
    val shelterId: Long,
    val shelterUserId: Long
)

data class CheckShelterUserEmailExistResponseDto(
    val isExist: Boolean
)
