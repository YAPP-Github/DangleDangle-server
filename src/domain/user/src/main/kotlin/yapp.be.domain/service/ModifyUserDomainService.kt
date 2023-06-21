package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.User
import yapp.be.domain.port.inbound.ModifyUserUseCase
import yapp.be.domain.port.inbound.model.ModifyUserTokenCommand
import yapp.be.domain.port.outbound.UserQueryHandler

@Service
class ModifyUserDomainService(
    private val userQueryHandler: UserQueryHandler
) : ModifyUserUseCase {
    @Transactional
    override fun updateUserToken(command: ModifyUserTokenCommand): User {
        val userEntity = userQueryHandler.findById(command.userId)
        val updatedUser = User(
            id = command.userId,
            email = userEntity.email,
            role = userEntity.role,
            oAuthType = userEntity.oAuthType,
            oAuthAccessToken = command.oAuth2SecurityToken.oAuth2AccessToken,
            oAuthRefreshToken = command.oAuth2SecurityToken.oAuth2RefreshToken,
            nickname = userEntity.nickname,
            phone = userEntity.phone,
        )
        return userQueryHandler.saveToken(updatedUser)
    }
}
