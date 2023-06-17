package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.User
import yapp.be.domain.port.inbound.GetUserUseCase
import yapp.be.domain.port.outbound.UserQueryHandler

@Service
class GetUserService(
    private val userQueryHandler: UserQueryHandler
) : GetUserUseCase {
    override fun getByEmail(email: String): User {
        return userQueryHandler.findByEmail(email)
    }
}
