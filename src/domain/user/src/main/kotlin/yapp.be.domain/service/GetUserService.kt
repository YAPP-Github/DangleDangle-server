package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.User
import yapp.be.domain.port.inbound.GetUserUseCase
import yapp.be.domain.port.outbound.UserQueryHandler
import yapp.be.exceptions.CustomException
import yapp.be.storage.config.exceptions.StorageExceptionType

@Service
class GetUserService(
    private val userQueryHandler: UserQueryHandler
) : GetUserUseCase {
    override fun getByEmail(email: String): User {
        return userQueryHandler.findByEmail(email) ?:
        throw CustomException(StorageExceptionType.ENTITY_NOT_FOUND, "해당 사용자가 존재하지 않습니다.")
    }
}
