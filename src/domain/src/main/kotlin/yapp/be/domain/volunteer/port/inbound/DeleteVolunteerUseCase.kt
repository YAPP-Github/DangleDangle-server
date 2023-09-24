package yapp.be.domain.volunteer.port.inbound

import yapp.be.domain.volunteer.model.Volunteer

interface DeleteVolunteerUseCase {
    fun deleteVolunteer(volunteerId: Long): Volunteer

    fun hardDeleteVolunteer(volunteerId: Long)
}
