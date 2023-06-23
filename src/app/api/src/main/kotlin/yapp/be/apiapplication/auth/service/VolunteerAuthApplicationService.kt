package yapp.be.apiapplication.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.auth.service.model.CheckUserNicknameExistResponseDto
import yapp.be.apiapplication.auth.service.model.LoginVolunteerRequestDto
import yapp.be.apiapplication.auth.service.model.SignUpUserRequestDto
import yapp.be.apiapplication.auth.service.model.SignUpUserWithEssentialInfoResponseDto
import yapp.be.apiapplication.auth.service.model.LoginVolunteerResponseDto
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.apiapplication.system.security.SecurityTokenType
import yapp.be.domain.port.inbound.CheckVolunteerUseCase
import yapp.be.domain.port.inbound.CreateVolunteerUseCase
import yapp.be.domain.port.inbound.SaveTokenUseCase
import yapp.be.domain.port.inbound.model.CreateUserCommand
import yapp.be.enum.Role
import yapp.be.exceptions.CustomException
import yapp.be.model.Email

@Service
class VolunteerAuthApplicationService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val createVolunteerUseCase: CreateVolunteerUseCase,
    private val checkVolunteerUseCase: CheckVolunteerUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
) {
    fun register(dto: SignUpUserRequestDto): SignUpUserWithEssentialInfoResponseDto {
        val user = createVolunteerUseCase.create(
            CreateUserCommand(
                nickname = dto.nickname,
                email = dto.email,
                phone = dto.phone
            )
        )

        return SignUpUserWithEssentialInfoResponseDto(
            userId = user.id,
        )
    }

    @Transactional(readOnly = true)
    fun checkIsUserNicknameExist(nickname: String): CheckUserNicknameExistResponseDto {
        val isExist = checkVolunteerUseCase.isExistByNickname(nickname)
        return CheckUserNicknameExistResponseDto(isExist)
    }

    @Transactional(readOnly = true)
    fun issueToken(reqDto: LoginVolunteerRequestDto): LoginVolunteerResponseDto {
        val claims = jwtTokenProvider.parseClaims(reqDto.authCode.replace("Bearer ", ""), SecurityTokenType.ACCESS)
        if (claims?.get("email")?.toString()?.equals(reqDto.email.value) == false) {
            throw CustomException(ApiExceptionType.UNAUTHENTICATED_EXCEPTION, "토큰의 이메일과 요청의 이메일이 일치하지 않습니다.")
        }

        val email = claims!!["email"].toString()
        val userId = claims["id"].toString().toLong()

        val tokens = jwtTokenProvider.generate(
            id = userId,
            email = Email(email),
            role = Role.VOLUNTEER
        )
        saveTokenUseCase.saveToken(tokens.accessToken, tokens.refreshToken)

        return LoginVolunteerResponseDto(
            accessToken = tokens.accessToken,
            refreshToken = tokens.refreshToken
        )
    }
}
