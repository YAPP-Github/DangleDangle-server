package yapp.be.domain.port.inbound

import yapp.be.domain.model.Iteration
import yapp.be.domain.model.VolunteerEvent

interface AddVolunteerEventUseCase {
    fun addVolunteerEventsWithIteration(
        iteration: Iteration,
        volunteerEvent: VolunteerEvent
    ): List<Long>

    fun addVolunteerEvent(
        volunteerEvent: VolunteerEvent
    ): List<Long>
}
