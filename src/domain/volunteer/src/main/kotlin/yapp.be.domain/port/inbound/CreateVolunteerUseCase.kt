package yapp.be.domain.port.inbound

import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.model.CreateUserCommand

interface CreateVolunteerUseCase {
    fun create(command: CreateUserCommand): Volunteer
}
