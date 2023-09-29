package yapp.be.apiapplication.auth.service

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.auth.service.model.CheckShelterUserSignUpDuplicationResponseDto
import yapp.be.apiapplication.auth.service.model.EditShelterUsePasswordResponseDto
import yapp.be.apiapplication.auth.service.model.LoginShelterUserRequestDto
import yapp.be.apiapplication.auth.service.model.LoginShelterUserResponseDto
import yapp.be.apiapplication.auth.service.model.SignUpShelterWithEssentialInfoRequestDto
import yapp.be.apiapplication.auth.service.model.SignUpShelterWithEssentialInfoResponseDto
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.apiapplication.system.security.SecurityTokenType
import yapp.be.apiapplication.system.security.properties.JwtConfigProperties
import yapp.be.domain.auth.port.inbound.SaveTokenUseCase
import yapp.be.domain.port.inbound.shelter.AddShelterUseCase
import yapp.be.domain.port.inbound.shelteruser.GetShelterUserUseCase
import yapp.be.domain.shelter.port.inbound.shelteruser.EditShelterUserUseCase
import yapp.be.domain.shelter.port.inbound.shelteruser.SignUpShelterUseCase
import yapp.be.model.enums.volunteerActivity.Role
import yapp.be.exceptions.CustomException
import yapp.be.model.vo.Email
import java.time.Duration

@Service
class ShelterAuthApplicationService(
    private val encoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val editShelterUserUseCase: EditShelterUserUseCase,
    private val addShelterUseCase: AddShelterUseCase,
    private val signUpShelterUseCase: SignUpShelterUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val jwtConfigProperties: JwtConfigProperties,
) {

    @Transactional
    fun login(reqDto: LoginShelterUserRequestDto): LoginShelterUserResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserByEmail(reqDto.email)

        if (shelterUser == null || !encoder.matches(reqDto.password, shelterUser.password)) {
            throw CustomException(ApiExceptionType.UNAUTHENTICATED_EXCEPTION, "Login Fail")
        }

        val accessToken = jwtTokenProvider.generateToken(
            id = shelterUser.id,
            email = shelterUser.email,
            role = Role.SHELTER,
            securityTokenType = SecurityTokenType.ACCESS
        )

        val refreshToken = jwtTokenProvider.generateToken(
            id = shelterUser.id,
            email = shelterUser.email,
            role = Role.SHELTER,
            securityTokenType = SecurityTokenType.REFRESH
        )

        saveTokenUseCase.saveToken(
            token = accessToken,
            value = refreshToken,
            expire = Duration.ofMillis(jwtConfigProperties.refresh.expire)
        )

        return LoginShelterUserResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            needToChangePassword = shelterUser.needToChangePassword
        )
    }

    @Transactional
    fun signUpWithEssentialInfo(reqDto: SignUpShelterWithEssentialInfoRequestDto): SignUpShelterWithEssentialInfoResponseDto {
        val shelter = addShelterUseCase.create(
            name = reqDto.name,
            address = reqDto.address,
            description = reqDto.description,
            phoneNumber = reqDto.phoneNumber,
        )
        val shelterUser = signUpShelterUseCase.signUpWithEssentialInfo(
            shelterId = shelter.id,
            email = reqDto.email,
            password = encoder.encode(reqDto.password),
            phoneNumber = reqDto.phoneNumber
        )

        return SignUpShelterWithEssentialInfoResponseDto(
            shelterId = shelter.id,
            shelterUserId = shelterUser.id
        )
    }

    @Transactional
    fun changePassword(shelterUserId: Long, password: String): EditShelterUsePasswordResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)
            .let {
                it.password = encoder.encode(password)
                it.needToChangePassword = false
                editShelterUserUseCase.editShelterUser(it)
            }
        return EditShelterUsePasswordResponseDto(
            shelterUserId = shelterUser.id,
            needToChangePassword = shelterUser.needToChangePassword
        )
    }

    @Transactional
    fun resetPassword(
        email: Email,
        phoneNumber: String
    ): EditShelterUsePasswordResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserByEmailAndPhoneNumber(
            email = email,
            phoneNumber = phoneNumber
        )?.let {
            val newPassword = generateRandomPassword()
            println(newPassword)
            it.password = encoder.encode(newPassword)
            it.needToChangePassword = true

            editShelterUserUseCase.editShelterUser(it)
        } ?: throw CustomException(ApiExceptionType.UNAUTHENTICATED_EXCEPTION, "Reset Password Fail")

        return EditShelterUsePasswordResponseDto(
            shelterUserId = shelterUser.id,
            needToChangePassword = shelterUser.needToChangePassword
        )
    }

    @Transactional(readOnly = true)
    fun checkIsShelterUserNameExist(name: String): CheckShelterUserSignUpDuplicationResponseDto {
        val isExist = getShelterUserUseCase.checkNameExist(name)
        return CheckShelterUserSignUpDuplicationResponseDto(isExist)
    }

    @Transactional(readOnly = true)
    fun checkIsShelterUserEmailExist(email: Email): CheckShelterUserSignUpDuplicationResponseDto {
        val isExist = getShelterUserUseCase.checkEmailExist(email)
        return CheckShelterUserSignUpDuplicationResponseDto(isExist)
    }

    private fun generateRandomPassword(): String {
        val specialCharacters = listOf<Char>(
            '~', '!', '@', '#', '$', '%', '^', '&', '*'
        )
        return "${RandomStringUtils.randomAlphanumeric(8,14)}${specialCharacters.random()}"
    }
}
