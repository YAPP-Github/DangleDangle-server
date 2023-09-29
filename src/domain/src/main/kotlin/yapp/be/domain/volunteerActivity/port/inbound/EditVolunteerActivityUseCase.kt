package yapp.be.domain.volunteerActivity.port.inbound

import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.port.inbound.model.EditVolunteerActivityCommand
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

interface EditVolunteerActivityUseCase {
    fun editVolunteerEvent(command: EditVolunteerActivityCommand): VolunteerActivity
    fun editVolunteerEventStatus(volunteerEventId: Long, status: VolunteerActivityStatus): VolunteerActivity
}
