package yapp.be.apiapplication.auth.service.model

import yapp.be.model.vo.Address
import yapp.be.model.vo.Email

class SignUpShelterWithEssentialInfoRequestDto(
    val email: Email,
    val password: String,
    val name: String,
    val phoneNumber: String,
    val description: String,
    val address: Address
)
data class SignUpShelterWithEssentialInfoResponseDto(
    val shelterId: Long,
    val shelterUserId: Long
)

data class CheckShelterUserSignUpDuplicationResponseDto(
    val isExist: Boolean
)
