package yapp.be.domain.port.outbound

import yapp.be.domain.model.ParticipateVolunteerEventEvent

interface ParticipateVolunteerEventEventCommandHandler {
    fun saveEvent(participateVolunteerEventEvent: ParticipateVolunteerEventEvent): ParticipateVolunteerEventEvent
}
