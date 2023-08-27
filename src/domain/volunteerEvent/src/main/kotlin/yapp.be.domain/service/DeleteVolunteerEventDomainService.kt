package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.DeleteVolunteerEventUseCase
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler

@Service
class DeleteVolunteerEventDomainService(
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler,
) : DeleteVolunteerEventUseCase {
    @Transactional
    override fun deleteByIdAndShelterId(id: Long, shelterId: Long) {
        volunteerEventCommandHandler.deleteVolunteerEventByIdAndShelterId(
            id = id,
            shelterId = shelterId
        )
    }

    @Transactional
    override fun deleteByShelterId(shelterId: Long) {
        val volunteerEventIds = volunteerEventCommandHandler.deleteAllVolunteerEventByShelterId(shelterId)
        volunteerEventIds.forEach {
            volunteerEventCommandHandler.deleteVolunteerEventWaitingQueueByVolunteerEventId(it)
            volunteerEventCommandHandler.deleteVolunteerEventJoinQueueByVolunteerEventId(it)
        }
    }

    @Transactional
    override fun deleteAllVolunteerRelatedVolunteerEvents(volunteerId: Long) {
        volunteerEventCommandHandler.deleteVolunteerEventJoiningQueueByVolunteerId(volunteerId)
        volunteerEventCommandHandler.deleteVolunteerEventWaitingQueueByVolunteerId(volunteerId)
    }

    @Transactional
    override fun hardDeleteByVolunteerId(volunteerId: Long) {
    }
}
