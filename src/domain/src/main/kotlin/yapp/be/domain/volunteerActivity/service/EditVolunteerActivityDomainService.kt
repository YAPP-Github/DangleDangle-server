package yapp.be.domain.volunteerActivity.service

import org.springframework.stereotype.Service
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.port.inbound.EditVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.inbound.model.EditVolunteerActivityCommand
import yapp.be.domain.volunteerActivity.port.outbound.VolunteerActivityCommandHandler
import yapp.be.domain.volunteerActivity.port.outbound.VolunteerActivityQueryHandler
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus

@Service
class EditVolunteerActivityDomainService(
    private val volunteerActivityCommandHandler: VolunteerActivityCommandHandler,
    private val volunteerActivityQueryHandler: VolunteerActivityQueryHandler,
) : EditVolunteerActivityUseCase {
    override fun editVolunteerEvent(command: EditVolunteerActivityCommand): VolunteerActivity {

        val updateVolunteerActivity = VolunteerActivity(
            id = command.volunteerEventId,
            shelterId = command.shelterId,
            title = command.title,
            recruitNum = command.recruitNum,
            description = command.description,
            ageLimit = command.ageLimit,
            startAt = command.startAt,
            endAt = command.endAt,
            volunteerActivityCategory = command.category,
            volunteerActivityStatus = command.status
        )

        return volunteerActivityCommandHandler.updateVolunteerActivity(updateVolunteerActivity)
    }

    override fun editVolunteerEventStatus(volunteerEventId: Long, status: VolunteerActivityStatus): VolunteerActivity {
        val volunteerEvent = volunteerActivityQueryHandler.findById(volunteerEventId)
        val updateVolunteerActivity = VolunteerActivity(
            id = volunteerEvent.id,
            shelterId = volunteerEvent.shelterId,
            title = volunteerEvent.title,
            recruitNum = volunteerEvent.recruitNum,
            description = volunteerEvent.description,
            ageLimit = volunteerEvent.ageLimit,
            startAt = volunteerEvent.startAt,
            endAt = volunteerEvent.endAt,
            volunteerActivityCategory = volunteerEvent.volunteerActivityCategory,
            volunteerActivityStatus = status
        )
        return volunteerActivityCommandHandler.updateVolunteerActivity(updateVolunteerActivity)
    }
}
