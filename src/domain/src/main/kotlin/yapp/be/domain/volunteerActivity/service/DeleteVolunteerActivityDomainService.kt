package yapp.be.domain.volunteerActivity.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteerActivity.port.inbound.DeleteVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.outbound.VolunteerActivityCommandHandler

@Service
class DeleteVolunteerActivityDomainService(
    private val volunteerActivityCommandHandler: VolunteerActivityCommandHandler,
) : DeleteVolunteerActivityUseCase {
    @Transactional
    override fun deleteByIdAndShelterId(id: Long, shelterId: Long) {
        volunteerActivityCommandHandler.deleteVolunteerActivityByIdAndShelterId(
            id = id,
            shelterId = shelterId
        )
    }

    @Transactional
    override fun deleteByShelterId(shelterId: Long) {
        val volunteerEventIds = volunteerActivityCommandHandler.deleteAllVolunteerEventByShelterId(shelterId)
        volunteerEventIds.forEach {
            volunteerActivityCommandHandler.deleteVolunteerActivityWaitingQueueByVolunteerActivityId(it)
            volunteerActivityCommandHandler.deleteVolunteerActivityJoiningQueueByVolunteerActivityId(it)
        }
    }

    @Transactional
    override fun deleteAllVolunteerRelatedVolunteerEvents(volunteerId: Long) {
        volunteerActivityCommandHandler.deleteVolunteerActivityJoiningQueueByVolunteerId(volunteerId)
        volunteerActivityCommandHandler.deleteVolunteerActivityWaitingQueueByVolunteerId(volunteerId)
    }

    @Transactional
    override fun hardDeleteByVolunteerId(volunteerId: Long) {
    }
}
