package yapp.be.apiapplication.volunteer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteer.model.Volunteer
import yapp.be.domain.volunteer.port.inbound.DeleteVolunteerUseCase
import yapp.be.domain.volunteer.port.inbound.GetVolunteerUseCase
import yapp.be.domain.volunteerActivity.port.inbound.DeleteVolunteerActivityUseCase

@Service
class VolunteerManageService(
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val deleteVolunteerUseCase: DeleteVolunteerUseCase,
    private val deleteVolunteerActivityUseCase: DeleteVolunteerActivityUseCase,
) {

    @Transactional(readOnly = true)
    fun getAllDeletedVolunteers(): List<Volunteer> {
        return getVolunteerUseCase.getAllDeletedVolunteers()
    }

    @Transactional
    fun deleteVolunteerAndAllRelatedContents(
        volunteerId: Long
    ) {
        deleteVolunteerActivityUseCase
            .deleteAllVolunteerRelatedVolunteerEvents(volunteerId)

        deleteVolunteerUseCase.hardDeleteVolunteer(volunteerId)
    }
}
