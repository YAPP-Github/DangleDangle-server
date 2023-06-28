package yapp.be.apiapplication.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.auth.service.model.CheckUserNicknameExistResponseDto
import yapp.be.apiapplication.auth.service.model.LoginVolunteerResponseDto
import yapp.be.apiapplication.auth.service.model.LoginVolunteerRequestDto
import yapp.be.apiapplication.auth.service.model.SignUpUserRequestDto
import yapp.be.apiapplication.auth.service.model.SignUpUserWithEssentialInfoResponseDto
import yapp.be.apiapplication.system.exception.ApiExceptionType
import yapp.be.apiapplication.system.security.properties.JwtConfigProperties
import yapp.be.domain.port.inbound.*
import yapp.be.domain.port.inbound.model.CreateUserCommand
import yapp.be.exceptions.CustomException

@Service
class VolunteerAuthApplicationService(
    private val getTokenUseCase: GetTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase,
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

    @Transactional
    fun issueToken(reqDto: LoginVolunteerRequestDto): LoginVolunteerResponseDto {
        val tokens = getTokenUseCase.getTokensByAuthToken(reqDto.authToken)
            ?.split(",")
            ?: throw CustomException(ApiExceptionType.UNAUTHORIZED_EXCEPTION, "AuthToken이 만료되었거나, 올바르지 않습니다.")

        saveTokenUseCase.saveToken(
            accessToken = tokens[0],
            refreshToken = tokens[1],
            expire = jwtConfigProperties.refresh.expire
        )

        deleteTokenUseCase.deleteTokenByAuthToken(authToken = reqDto.authToken)

        return LoginVolunteerResponseDto(
            accessToken = tokens[0],
            refreshToken = tokens[1]
        )
    }
}
