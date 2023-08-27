package yapp.be.apiapplication.volunteer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.Volunteer
import yapp.be.domain.port.inbound.DeleteVolunteerEventUseCase
import yapp.be.domain.port.inbound.DeleteVolunteerUseCase
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.GetVolunteerUseCase

@Service
class VolunteerManageService(
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase,
    private val deleteVolunteerUseCase: DeleteVolunteerUseCase,
    private val deleteVolunteerEventUseCase: DeleteVolunteerEventUseCase,
) {

    @Transactional(readOnly = true)
    fun getAllDeletedVolunteers(): List<Volunteer> {
        return getVolunteerUseCase.getAllDeletedVolunteers()
    }

    @Transactional
    fun deleteVolunteerAndAllRelatedContents(
        volunteerId: Long
    ) {
        deleteVolunteerEventUseCase
            .deleteAllVolunteerRelatedVolunteerEvents(volunteerId)

        deleteVolunteerUseCase.hardDeleteVolunteer(volunteerId)
    }
}
