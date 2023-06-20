package yapp.be.apiapplication.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.auth.service.model.CheckShelterUserSignUpDuplicationResponseDto
import yapp.be.apiapplication.auth.service.model.LoginShelterUserRequestDto
import yapp.be.apiapplication.auth.service.model.LoginShelterUserResponseDto
import yapp.be.apiapplication.auth.service.model.SignUpShelterWithEssentialInfoRequestDto
import yapp.be.apiapplication.auth.service.model.SignUpShelterWithEssentialInfoResponseDto
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.domain.port.inbound.CreateShelterUseCase
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.domain.port.inbound.SignUpShelterUseCase
import yapp.be.enum.Role
import yapp.be.exceptions.CustomException
import yapp.be.model.Email

@Service
class ShelterAuthApplicationService(
    private val encoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val createShelterUseCase: CreateShelterUseCase,
    private val signUpShelterUseCase: SignUpShelterUseCase
) {

    @Transactional(readOnly = true)
    fun login(reqDto: LoginShelterUserRequestDto): LoginShelterUserResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserByEmail(reqDto.email)

        if (shelterUser == null || !encoder.matches(reqDto.password, shelterUser.password)) {
            throw CustomException(ApiExceptionType.UNAUTHENTICATED_EXCEPTION, "Login Fail")
        }

        val tokens = jwtTokenProvider.generate(
            id = shelterUser.id,
            email = shelterUser.email,
            role = Role.SHELTER
        )

        return LoginShelterUserResponseDto(
            accessToken = tokens.accessToken,
            refreshToken = tokens.refreshToken
        )
    }

    @Transactional
    fun signUpWithEssentialInfo(reqDto: SignUpShelterWithEssentialInfoRequestDto): SignUpShelterWithEssentialInfoResponseDto {
        val shelter = createShelterUseCase.create(
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
}
