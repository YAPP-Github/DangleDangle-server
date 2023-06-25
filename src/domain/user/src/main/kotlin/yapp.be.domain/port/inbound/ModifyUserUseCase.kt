package yapp.be.domain.port.inbound

import yapp.be.domain.model.User
import yapp.be.domain.port.inbound.model.ModifyUserTokenCommand

interface ModifyUserUseCase {
    fun updateUserToken(command: ModifyUserTokenCommand): User
}
