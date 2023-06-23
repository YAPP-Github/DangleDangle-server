package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.User
import yapp.be.domain.port.inbound.GetUserUseCase
import yapp.be.domain.port.outbound.UserQueryHandler

@Service
class GetUserDomainService(
    private val userQueryHandler: UserQueryHandler
) : GetUserUseCase {
    @Transactional(readOnly = true)
    override fun getByEmail(email: String): User {
        return userQueryHandler.findByEmail(email)
    }
}
