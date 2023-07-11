package yapp.be.domain.service

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.port.inbound.EditVolunteerEventUseCase
import yapp.be.domain.port.inbound.model.EditVolunteerEventCommand
import yapp.be.domain.port.outbound.VolunteerEventCommandHandler
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.domain.service.exceptions.VolunteerEventExceptionType
import yapp.be.exceptions.CustomException

@Service
class EditVolunteerEventDomainService(
    private val volunteerEventQueryHandler: VolunteerEventQueryHandler,
    private val volunteerEventCommandHandler: VolunteerEventCommandHandler
) : EditVolunteerEventUseCase {
    override fun editVolunteerEvent(command: EditVolunteerEventCommand): VolunteerEvent {
        val now = LocalDateTime.now()
        val volunteerEvent =
            volunteerEventQueryHandler
                .findDetailVolunteerEventInfoByIdAndShelterId(
                    id = command.volunteerEventId,
                    shelterId = command.shelterId
                )

        if (volunteerEvent.joiningVolunteers.size > command.recruitNum || command.recruitNum == 0) {
            throw CustomException(
                type = VolunteerEventExceptionType.INVALID_RECRUIT_NUM_EDIT,
                message = "정원을 현재 참여 중 인원보다 적게 수정할 수 없습니다."
            )
        }

        if (command.endAt.isBefore(command.startAt) || command.startAt.isBefore(now) || command.endAt.isBefore(now)) {
            throw CustomException(
                type = VolunteerEventExceptionType.INVALID_DATE_RANGE_EDIT,
                message = "잘못된 날짜 설정입니다."
            )
        }

        val updateVolunteerEvent = VolunteerEvent(
            id = volunteerEvent.id,
            shelterId = command.shelterId,
            title = command.title,
            recruitNum = command.recruitNum,
            description = command.description,
            ageLimit = command.ageLimit,
            startAt = command.startAt,
            endAt = command.endAt,
            volunteerEventCategory = command.category,
            volunteerEventStatus = command.status
        )

        return volunteerEventCommandHandler.updateVolunteerEvent(updateVolunteerEvent)
    }
}
