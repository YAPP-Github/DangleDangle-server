package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.port.inbound.EditVolunteerEventUseCase
import yapp.be.domain.port.inbound.model.EditVolunteerEventCommand
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus

@Service
class EditVolunteerEventDomainService(
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler,
    private val volunteerEventQueryHandler: VolunteerEventQueryHandler,
) : EditVolunteerEventUseCase {
    override fun editVolunteerEvent(command: EditVolunteerEventCommand): VolunteerEvent {

        val updateVolunteerEvent = VolunteerEvent(
            id = command.volunteerEventId,
            shelterId = command.shelterId,
            title = command.title,
            recruitNum = command.recruitNum,
            description = command.description,
            ageLimit = command.ageLimit,
            startAt = command.startAt,
            endAt = command.endAt,
            volunteerEventCategory = command.category,
            volunteerEventStatus = command.status
        )

        return volunteerEventCommandHandler.updateVolunteerEvent(updateVolunteerEvent)
    }

    override fun editVolunteerEventStatus(volunteerEventId: Long, status: VolunteerEventStatus): VolunteerEvent {
        val volunteerEvent = volunteerEventQueryHandler.findById(volunteerEventId)
        val updateVolunteerEvent = VolunteerEvent(
            id = volunteerEvent.id,
            shelterId = volunteerEvent.shelterId,
            title = volunteerEvent.title,
            recruitNum = volunteerEvent.recruitNum,
            description = volunteerEvent.description,
            ageLimit = volunteerEvent.ageLimit,
            startAt = volunteerEvent.startAt,
            endAt = volunteerEvent.endAt,
            volunteerEventCategory = volunteerEvent.volunteerEventCategory,
            volunteerEventStatus = status
        )
        return volunteerEventCommandHandler.updateVolunteerEvent(updateVolunteerEvent)
    }
}
