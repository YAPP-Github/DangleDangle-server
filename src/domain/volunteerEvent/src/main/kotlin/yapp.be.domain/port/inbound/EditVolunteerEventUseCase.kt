package yapp.be.domain.port.inbound

import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.port.inbound.model.EditVolunteerEventCommand
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus

interface EditVolunteerEventUseCase {
    fun editVolunteerEvent(command: EditVolunteerEventCommand): VolunteerEvent
    fun editVolunteerEventStatus(volunteerEventId: Long, status: VolunteerEventStatus): VolunteerEvent
}
