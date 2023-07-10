package yapp.be.domain.service

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Iteration
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.port.inbound.AddVolunteerEventUseCase
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler

@Service
class AddVolunteerEventDomainService(
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler
) : AddVolunteerEventUseCase {
    @Transactional
    override fun addVolunteerEventsWithIteration(iteration: Iteration, volunteerEvent: VolunteerEvent): List<Long> {
        var startAt: LocalDateTime = volunteerEvent.startAt
        var endAt: LocalDateTime = volunteerEvent.endAt
        val volunteerEvents = mutableListOf<VolunteerEvent>()

        while (canIterate(
                startAt = startAt,
                iteration = iteration
            )
        ) {
            volunteerEvents.add(volunteerEvent.copy(startAt = startAt, endAt = endAt))
            startAt = iteration.cycle.getNextDate(startAt)
            endAt = iteration.cycle.getNextDate(endAt)
        }

        return volunteerEventCommandHandler
            .saveAllVolunteerEvents(volunteerEvents)
            .map { it.id }.toList()
    }

    @Transactional
    override fun addVolunteerEvent(volunteerEvent: VolunteerEvent): List<Long> {
        return listOf(volunteerEventCommandHandler.saveVolunteerEvent(volunteerEvent).id)
    }

    private fun canIterate(startAt: LocalDateTime, iteration: Iteration): Boolean {
        val startDate = startAt.toLocalDate()

        return !startDate.isAfter(iteration.iterationEndAt)
    }
}
