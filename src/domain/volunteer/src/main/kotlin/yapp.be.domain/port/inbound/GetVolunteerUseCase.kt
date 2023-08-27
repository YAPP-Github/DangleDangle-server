package yapp.be.domain.port.inbound

import yapp.be.domain.model.Volunteer
import yapp.be.model.vo.Email

interface GetVolunteerUseCase {

    fun getById(volunteerId: Long): Volunteer
    fun getByEmail(email: Email): Volunteer

    fun getAllDeletedVolunteers(): List<Volunteer>
}
