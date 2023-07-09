package yapp.be.domain.port.inbound

import yapp.be.domain.model.Volunteer
import yapp.be.model.vo.Email

interface GetVolunteerUseCase {
    fun getByEmail(email: Email): Volunteer
}
