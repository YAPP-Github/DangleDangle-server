package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.User
import yapp.be.domain.port.inbound.ModifyUserUseCase
import yapp.be.domain.port.inbound.model.ModifyUserTokenCommand
import yapp.be.domain.port.outbound.UserQueryHandler
import yapp.be.domain.port.outbound.model.ModifyUserTokenRequest

@Service
class ModifyUserService(
    private val userQueryHandler: UserQueryHandler
) : ModifyUserUseCase {
    override fun updateUserToken(command: ModifyUserTokenCommand): User {
        val userEntity = userQueryHandler.findById(command.userId.toLong())
        val modifyEntity = ModifyUserTokenRequest(
            userId = command.userId,
            oAuth2AccessToken = command.oAuth2SecurityToken.oAuth2AccessToken,
            oAuth2RefreshToken = command.oAuth2SecurityToken.oAuth2RefreshToken,
            nickname = userEntity.nickname,
            email = userEntity.email,
            phone = userEntity.phone,
        )
        return userQueryHandler.saveToken(modifyEntity)
    }
}
