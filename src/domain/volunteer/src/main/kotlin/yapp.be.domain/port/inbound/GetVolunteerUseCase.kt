package yapp.be.domain.port.inbound

import yapp.be.domain.model.Volunteer

interface GetVolunteerUseCase {
    fun getByEmail(email: String): Volunteer
}
