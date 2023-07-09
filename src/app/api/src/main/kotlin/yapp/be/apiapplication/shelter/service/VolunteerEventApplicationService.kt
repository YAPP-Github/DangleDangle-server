package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.GetDetailVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.GetSimpleVolunteerEventResponseDto
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventsRequestDto
import yapp.be.apiapplication.shelter.service.model.GetVolunteerEventsResponseDto
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase

@Service
class VolunteerEventApplicationService(
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase,
) {

    @Transactional(readOnly = true)
    fun getVolunteerEvent(
        reqDto: GetVolunteerEventRequestDto
    ): GetDetailVolunteerEventResponseDto {
        val volunteerEvent = getVolunteerEventUseCase
            .getVolunteerEvent(
                shelterId = reqDto.shelterId,
                volunteerEventId = reqDto.volunteerEventId
            )

        return GetDetailVolunteerEventResponseDto(
            title = volunteerEvent.title,
            recruitNum = volunteerEvent.recruitNum,
            address = volunteerEvent.address,
            description = volunteerEvent.description,
            ageLimit = volunteerEvent.ageLimit,
            category = volunteerEvent.category,
            eventStatus = volunteerEvent.eventStatus,
            myParticipationStatus = volunteerEvent.myParticipationStatus,
            startAt = volunteerEvent.startAt,
            endAt = volunteerEvent.endAt,
            joiningVolunteers = volunteerEvent.joiningVolunteers,
            waitingVolunteers = volunteerEvent.waitingVolunteers
        )
    }

    @Transactional(readOnly = true)
    fun getVolunteerEvents(
        reqDto: GetVolunteerEventsRequestDto
    ): GetVolunteerEventsResponseDto {
        val volunteerEvents = if (reqDto.volunteerId != null) {
            getVolunteerEventUseCase.getMemberVolunteerEventsByYearAndMonth(
                shelterId = reqDto.shelterId,
                volunteerId = reqDto.volunteerId,
                from = reqDto.from,
                to = reqDto.to
            )
        } else {
            getVolunteerEventUseCase.getNonMemberVolunteerEventsByYearAndMonth(
                shelterId = reqDto.shelterId,
                from = reqDto.from,
                to = reqDto.to
            )
        }

        return GetVolunteerEventsResponseDto(
            events = volunteerEvents.map {
                GetSimpleVolunteerEventResponseDto(
                    volunteerEventId = it.volunteerEventId,
                    category = it.category,
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
