package yapp.be.domain.port.inbound

import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.port.inbound.model.EditVolunteerEventCommand

interface EditVolunteerEventUseCase {
    fun editVolunteerEvent(command: EditVolunteerEventCommand): VolunteerEvent
}
