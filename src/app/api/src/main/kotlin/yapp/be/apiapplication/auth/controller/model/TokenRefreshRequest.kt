package yapp.be.apiapplication.auth.controller.model

import jakarta.validation.constraints.NotBlank
import yapp.be.apiapplication.auth.service.model.TokenRefreshRequestDto

data class TokenRefreshRequest(
    @field:NotBlank(message = "값이 비어있습니다.")
    val accessToken: String,
    @field:NotBlank(message = "값이 비어있습니다.")
    val refreshToken: String,
) {
    fun toDto(): TokenRefreshRequestDto {
        return TokenRefreshRequestDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
}
