package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.User
import yapp.be.domain.port.inbound.CreateUserUseCase
import yapp.be.domain.port.inbound.model.CreateUserCommand
import yapp.be.domain.port.outbound.UserQueryHandler

@Service
class CreateUserDomainService(
    private val userQueryHandler: UserQueryHandler
) : CreateUserUseCase {
    override fun create(command: CreateUserCommand): User {
        val user = User(
            nickname = command.nickname,
            email = command.email,
            phone = command.phone,
        )
        return userQueryHandler.save(user)
    }
}
