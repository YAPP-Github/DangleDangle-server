package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.AddVolunteerEventEvent
import yapp.be.domain.model.Event
import yapp.be.domain.model.ParticipateVolunteerEventEvent
import yapp.be.domain.model.WithDrawVolunteerEventEvent
import yapp.be.domain.port.inbound.AddEventUseCase
import yapp.be.domain.port.outbound.AddVolunteerEventEventCommandHandler
import yapp.be.domain.port.outbound.ParticipateVolunteerEventEventCommandHandler
import yapp.be.domain.port.outbound.WithDrawVolunteerEventEventCommandHandler

@Service
class AddEventDomainService(
    private val addVolunteerEventEventCommandHandler: AddVolunteerEventEventCommandHandler,
    private val participateVolunteerEventEventCommandHandler: ParticipateVolunteerEventEventCommandHandler,
    private val withDrawVolunteerEventEventCommandHandler: WithDrawVolunteerEventEventCommandHandler,
) : AddEventUseCase {
    @Transactional
    override fun addEvent(event: Event): Event {
        return when (event) {
            is AddVolunteerEventEvent -> addVolunteerEventEventCommandHandler.saveEvent(event)
            is ParticipateVolunteerEventEvent -> participateVolunteerEventEventCommandHandler.saveEvent(event)
            is WithDrawVolunteerEventEvent -> withDrawVolunteerEventEventCommandHandler.saveEvent(event)
        }
    }
}
