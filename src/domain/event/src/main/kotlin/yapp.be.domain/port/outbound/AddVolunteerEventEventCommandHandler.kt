package yapp.be.domain.port.outbound

import yapp.be.domain.model.AddVolunteerEventEvent

interface AddVolunteerEventEventCommandHandler {
    fun saveEvent(addVolunteerEventEvent: AddVolunteerEventEvent): AddVolunteerEventEvent
}
