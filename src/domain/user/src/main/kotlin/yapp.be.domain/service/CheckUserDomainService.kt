package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.CheckUserUseCase
import yapp.be.domain.port.outbound.UserQueryHandler

@Service
class CheckUserDomainService(
    private val userQueryHandler: UserQueryHandler
) : CheckUserUseCase {
    @Transactional(readOnly = true)
    override fun isExistByEmail(email: String): Boolean {
        return userQueryHandler.isExistByEmail(email)
    }
    @Transactional(readOnly = true)
    override fun isExistByNickname(nickname: String): Boolean {
        return userQueryHandler.isExistByNickname(nickname)
    }
}
