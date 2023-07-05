package yapp.be.apiapplication.shelter.service.shelter

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.shelter.model.GetVolunteerEventsRequestDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetVolunteerEventsResponseDto
import yapp.be.apiapplication.shelter.service.shelter.model.GetVolunteerSimpleEventResponseDto
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase

@Service
class VolunteerEventApplicationService(
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase,
) {
    @Transactional(readOnly = true)
    fun getVolunteerEvents(
        reqDto: GetVolunteerEventsRequestDto
    ): GetVolunteerEventsResponseDto {
        val volunteerEvents = if (reqDto.volunteerId != null) {
            getVolunteerEventUseCase.getMemberVolunteerEvents()
        } else {
            getVolunteerEventUseCase.getNonMemberVolunteerEvents()
        }

        return GetVolunteerEventsResponseDto(
            events = volunteerEvents.map {
                GetVolunteerSimpleEventResponseDto(
                    volunteerEventId = it.volunteerEventId,
                    title = it.title,
                    eventStatus = it.eventStatus,
                    myParticipationStatus = it.myParticipationStatus,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    recruitNum = it.recruitNum,
                    participantNum = it.participantNum,
                    waitingNum = it.waitingNum
                )
            }
        )
    }
}
