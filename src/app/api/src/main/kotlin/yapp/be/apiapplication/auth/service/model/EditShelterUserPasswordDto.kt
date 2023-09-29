package yapp.be.apiapplication.auth.service.model

data class EditShelterUsePasswordResponseDto(
    val shelterUserId: Long,
    val needToChangePassword: Boolean
)
