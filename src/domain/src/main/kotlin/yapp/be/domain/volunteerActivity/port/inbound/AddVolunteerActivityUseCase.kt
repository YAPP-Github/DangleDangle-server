package yapp.be.domain.volunteerActivity.port.inbound

import yapp.be.domain.volunteerActivity.model.Iteration
import yapp.be.domain.volunteerActivity.model.VolunteerActivity

interface AddVolunteerActivityUseCase {
    fun addVolunteerEventsWithIteration(
        iteration: Iteration,
        volunteerActivity: VolunteerActivity
    ): List<Long>

    fun addVolunteerEvent(
        volunteerActivity: VolunteerActivity
    ): List<Long>
}
