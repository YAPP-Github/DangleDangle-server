package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.port.inbound.CreateUserUseCase
import yapp.be.domain.port.inbound.model.CreateUserCommand
import yapp.be.domain.port.outbound.UserQueryHandler
import yapp.be.domain.port.outbound.model.CreateUserRequest

@Service
class CreateUserService(
    private val userQueryHandler: UserQueryHandler
) : CreateUserUseCase {
    override fun create(command: CreateUserCommand): Boolean {
        val request = CreateUserRequest(
            nickname = command.nickname,
            email = command.email,
            phone = command.phone,
            oAuthAccessToken = command.oAuthAccessToken,
        )
        val user = userQueryHandler.save(request)
        return user != null
    }
}
