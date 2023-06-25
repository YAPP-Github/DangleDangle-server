package yapp.be.domain.port.inbound

import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.model.ModifyUserTokenCommand

interface ModifyVolunteerUseCase {
    fun updateUserToken(command: ModifyUserTokenCommand): Volunteer
}
