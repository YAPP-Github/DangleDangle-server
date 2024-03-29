package yapp.be.apiapplication.auth.controller.model

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import kotlin.math.max
import org.hibernate.validator.constraints.Length
import yapp.be.apiapplication.auth.service.model.SignUpShelterWithEssentialInfoRequestDto
import yapp.be.model.support.EMAIL_REGEX
import yapp.be.model.support.PASSWORD_REGEX
import yapp.be.model.support.PHONE_REGEX
import yapp.be.model.vo.Address
import yapp.be.model.vo.Email

data class SignUpWithEssentialInfoRequest(
    @field:Pattern(regexp = EMAIL_REGEX, message = "잘못된 이메일 형식입니다.")
    val email: String,
    @field:Pattern(
        regexp = PASSWORD_REGEX,
        message = "비밀번호는 영문, 숫자, 특수문자 2가지 조합 8~15자이어야 합니다."
    )
    val password: String,
    @field:NotBlank(message = "값이 비어있습니다.")
    val name: String,
    @field:Pattern(
        regexp = PHONE_REGEX,
        message = "올바른 전화번호 형식인지 확인해주세요. (- 제외 필요)"
    )
    val phoneNumber: String,
    @field:Length(max = 300, message = "입력 가능 글자수를 초과했습니다.")
    @field:NotBlank(message = "값이 비어있습니다.")
    val description: String,
    @field:Valid
    val address: ShelterSignUpAddressInfo,
) {
    fun toDto(): SignUpShelterWithEssentialInfoRequestDto {
        return SignUpShelterWithEssentialInfoRequestDto(
            email = Email(email),
            password = password,
            name = name,
            phoneNumber = phoneNumber,
            description = description,
            address = Address(
                address = this.address.address,
                addressDetail = this.address.addressDetail,
                postalCode = this.address.postalCode,
                latitude = this.address.latitude,
                longitude = this.address.longitude
            )
        )
    }
}

data class ShelterSignUpAddressInfo(
    @field:NotBlank(message = "주소 값이 비어있습니다.")
    val address: String,
    @field:NotBlank(message = "상세주소 값이 비어있습니다.")
    val addressDetail: String,
    @field:Length(min = 5, max = 5, message = "우편번호는 5자리 입니다.")
    val postalCode: String,
    @field:NotNull(message = "위도 값이 비어있습니다.")
    val latitude: Double,
    @field:NotNull(message = "경도 값이 비어있습니다.")
    val longitude: Double
)

enum class ShelterSignUpCheckDuplicationType {
    EMAIL, NAME
}
