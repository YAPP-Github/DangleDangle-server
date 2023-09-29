package yapp.be.domain.volunteer.port.outbound

import yapp.be.domain.volunteer.model.Volunteer

interface VolunteerCommandHandler {
    fun save(volunteer: Volunteer): Volunteer
    fun delete(volunteerId: Long): Volunteer
    fun update(volunteer: Volunteer): Volunteer

    fun hardDelete(volunteerId: Long)
}
