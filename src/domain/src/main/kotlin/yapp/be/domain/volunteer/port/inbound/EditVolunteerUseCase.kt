package yapp.be.domain.volunteer.port.inbound

import yapp.be.domain.volunteer.port.inbound.model.EditUserTokenCommand
import yapp.be.domain.volunteer.model.Volunteer
import yapp.be.domain.volunteer.port.inbound.model.EditVolunteerCommand

interface EditVolunteerUseCase {

    fun updateVolunteer(command: EditVolunteerCommand): Volunteer
    fun updateUserToken(command: EditUserTokenCommand): Volunteer
}
