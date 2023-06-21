package yapp.be.apiapplication.auth.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.auth.service.model.CheckUserNicknameExistResponseDto
import yapp.be.apiapplication.auth.service.model.SignUpUserRequestDto
import yapp.be.apiapplication.auth.service.model.SignUpUserWithEssentialInfoResponseDto
import yapp.be.domain.port.inbound.CheckUserUseCase
import yapp.be.domain.port.inbound.CreateUserUseCase
import yapp.be.domain.port.inbound.model.CreateUserCommand

@Service
class UserAuthApplicationService(
    private val createUserUseCase: CreateUserUseCase,
    private val checkUserUseCase: CheckUserUseCase,
) {
    fun register(dto: SignUpUserRequestDto): SignUpUserWithEssentialInfoResponseDto {
        val user = createUserUseCase.create(
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
        val isExist = checkUserUseCase.isExistByNickname(nickname)
        return CheckUserNicknameExistResponseDto(isExist)
    }
}
