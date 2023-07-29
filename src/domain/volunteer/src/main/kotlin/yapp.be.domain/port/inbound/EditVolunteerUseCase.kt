package yapp.be.domain.port.inbound

import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.model.EditUserTokenCommand
import yapp.be.domain.port.inbound.model.EditVolunteerCommand

interface EditVolunteerUseCase {

    fun updateVolunteer(command: EditVolunteerCommand): Volunteer
    fun updateUserToken(command: EditUserTokenCommand): Volunteer
}
