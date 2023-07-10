package yapp.be.domain.port.outbound

import yapp.be.domain.model.VolunteerEvent

interface VolunteerEventCommandHandler {
    fun save(volunteerEvent: VolunteerEvent): VolunteerEvent
    fun saveAll(volunteerEvents: Collection<VolunteerEvent>): List<VolunteerEvent>

    fun deleteByIdAndShelterId(id: Long, shelterId: Long)
}
