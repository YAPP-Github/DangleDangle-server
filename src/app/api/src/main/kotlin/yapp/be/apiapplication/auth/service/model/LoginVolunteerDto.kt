package yapp.be.apiapplication.auth.service.model

import yapp.be.model.enums.volunteerevent.Role

data class LoginVolunteerRequestDto(
    val authToken: String
)

class LoginVolunteerResponseDto(
    val role: Role,
    val accessToken: String,
    val refreshToken: String
)
