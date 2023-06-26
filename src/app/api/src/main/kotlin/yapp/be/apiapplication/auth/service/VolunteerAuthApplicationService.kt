package yapp.be.apiapplication.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.auth.service.model.*
import yapp.be.apiapplication.system.security.JwtConfigProperties
import yapp.be.apiapplication.system.security.JwtTokenProvider
import yapp.be.apiapplication.system.security.SecurityTokenType
import yapp.be.domain.port.inbound.CheckVolunteerUseCase
import yapp.be.domain.port.inbound.CreateVolunteerUseCase
import yapp.be.domain.port.inbound.SaveTokenUseCase
import yapp.be.domain.port.inbound.model.CreateUserCommand
import yapp.be.enum.Role
import yapp.be.model.Email

@Service
class VolunteerAuthApplicationService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val createVolunteerUseCase: CreateVolunteerUseCase,
    private val checkVolunteerUseCase: CheckVolunteerUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val jwtConfigProperties: JwtConfigProperties,
) {
    @Transactional
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

        val email = claims!!["email"].toString()
        val userId = claims["id"].toString().toLong()
        val tokens = jwtTokenProvider.generate(
            id = userId,
            email = Email(email),
            role = Role.VOLUNTEER
        )
        saveTokenUseCase.saveToken(tokens.accessToken, tokens.refreshToken, jwtConfigProperties.refresh.expire)
        return LoginVolunteerResponseDto(
            accessToken = tokens.accessToken,
            refreshToken = tokens.refreshToken
        )
    }
}
