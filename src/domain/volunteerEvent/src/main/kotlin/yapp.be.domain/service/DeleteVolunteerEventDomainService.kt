package yapp.be.domain.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.port.inbound.DeleteVolunteerEventUseCase
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler

@Service
class DeleteVolunteerEventDomainService(
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler
) : DeleteVolunteerEventUseCase {
    @Transactional
    override fun deleteByIdAndShelterId(id: Long, shelterId: Long) {
        volunteerEventCommandHandler.deleteByIdAndShelterId(
            id = id,
            shelterId = shelterId
        )
    }
}
