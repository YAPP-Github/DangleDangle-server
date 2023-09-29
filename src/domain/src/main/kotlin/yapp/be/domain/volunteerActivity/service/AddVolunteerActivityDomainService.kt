package yapp.be.domain.volunteerActivity.service

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteerActivity.model.Iteration
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.port.inbound.AddVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.outbound.VolunteerActivityCommandHandler

@Service
class AddVolunteerActivityDomainService(
    private val volunteerActivityCommandHandler: VolunteerActivityCommandHandler
) : AddVolunteerActivityUseCase {
    @Transactional
    override fun addVolunteerEventsWithIteration(iteration: Iteration, volunteerActivity: VolunteerActivity): List<Long> {
        var startAt: LocalDateTime = volunteerActivity.startAt
        var endAt: LocalDateTime = volunteerActivity.endAt
        val volunteerActivities = mutableListOf<VolunteerActivity>()

        while (canIterate(
                startAt = startAt,
                iteration = iteration
            )
        ) {
            volunteerActivities.add(volunteerActivity.copy(startAt = startAt, endAt = endAt))
            startAt = iteration.cycle.getNextDate(startAt)
            endAt = iteration.cycle.getNextDate(endAt)
        }

        return volunteerActivityCommandHandler
            .saveAllVolunteerActivities(volunteerActivities)
            .map { it.id }.toList()
    }

    @Transactional
    override fun addVolunteerEvent(volunteerActivity: VolunteerActivity): List<Long> {
        return listOf(volunteerActivityCommandHandler.saveVolunteerActivity(volunteerActivity).id)
    }

    private fun canIterate(startAt: LocalDateTime, iteration: Iteration): Boolean {
        val startDate = startAt.toLocalDate()

        return !startDate.isAfter(iteration.iterationEndAt)
    }
}
