package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.CheckUserUseCase
import yapp.be.domain.port.outbound.UserQueryHandler

@Service
class CheckUserService(
    private val userQueryHandler: UserQueryHandler
) : CheckUserUseCase {
    override fun isExistByEmail(email: String): Boolean {
        return userQueryHandler.isExistByEmail(email)
    }

    override fun isExistByNickname(nickname: String): Boolean {
        return userQueryHandler.isExistByNickname(nickname)
    }
}
