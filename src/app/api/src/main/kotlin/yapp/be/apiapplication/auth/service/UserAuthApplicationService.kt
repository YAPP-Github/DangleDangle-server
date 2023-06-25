package yapp.be.apiapplication.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.auth.service.model.CheckUserNicknameExistResponseDto
import yapp.be.apiapplication.auth.service.model.SignUpUserRequestDto
import yapp.be.apiapplication.auth.service.model.SignUpUserWithEssentialInfoResponseDto
import yapp.be.domain.port.inbound.CheckVolunteerUseCase
import yapp.be.domain.port.inbound.CreateVolunteerUseCase
import yapp.be.domain.port.inbound.model.CreateUserCommand

@Service
class UserAuthApplicationService(
    private val createVolunteerUseCase: CreateVolunteerUseCase,
    private val checkVolunteerUseCase: CheckVolunteerUseCase,
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
}
