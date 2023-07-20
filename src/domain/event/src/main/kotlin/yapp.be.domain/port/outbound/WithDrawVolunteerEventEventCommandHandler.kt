package yapp.be.domain.port.outbound

import yapp.be.domain.model.WithDrawVolunteerEventEvent

interface WithDrawVolunteerEventEventCommandHandler {
    fun saveEvent(withDrawVolunteerEventEvent: WithDrawVolunteerEventEvent): WithDrawVolunteerEventEvent
}
