package yapp.be.domain.volunteer.port.inbound

import yapp.be.domain.volunteer.model.Volunteer
import yapp.be.domain.volunteer.port.inbound.model.CreateUserCommand

interface CreateVolunteerUseCase {
    fun create(command: CreateUserCommand): Volunteer
}
