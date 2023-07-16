package yapp.be.domain.service

import org.springframework.stereotype.Service
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.port.inbound.EditVolunteerEventUseCase
import yapp.be.domain.port.inbound.model.EditVolunteerEventCommand
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler

@Service
class EditVolunteerEventDomainService(
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler
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
}
