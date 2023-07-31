package yapp.be.apiapplication.volunteer.controller.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import yapp.be.apiapplication.volunteer.service.model.EditVolunteerMyProfileRequestDto
import yapp.be.model.support.PHONE_REGEX

data class EditVolunteerMyProfileRequest(
    @field:NotBlank(message = "닉네임 값이 비어있습니다.")
    val nickName: String,
    @field:Pattern(
        regexp = PHONE_REGEX,
        message = "잘못된 번호 형식입니다."
    )
    val phoneNumber: String,
    val alarmEnabled: Boolean
) {
    fun toDto(): EditVolunteerMyProfileRequestDto {
        return EditVolunteerMyProfileRequestDto(
            nickName = this.nickName,
            phoneNumber = this.phoneNumber,
            alarmEnabled = this.alarmEnabled
        )
    }
}
