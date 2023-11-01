package yapp.be.domain.volunteer.port.inbound

import yapp.be.domain.volunteer.model.Volunteer
import yapp.be.model.vo.Email

interface GetVolunteerUseCase {

    fun getById(volunteerId: Long): Volunteer
    fun getByEmail(email: Email): Volunteer

    fun getAllByIds(ids: List<Long>): List<Volunteer>

    fun getAllDeletedVolunteers(): List<Volunteer>
}
