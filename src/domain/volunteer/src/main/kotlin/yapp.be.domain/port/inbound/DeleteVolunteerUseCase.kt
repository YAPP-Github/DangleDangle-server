package yapp.be.domain.port.inbound

import yapp.be.domain.model.Volunteer

interface DeleteVolunteerUseCase {
    fun deleteVolunteer(volunteerId: Long): Volunteer
}
