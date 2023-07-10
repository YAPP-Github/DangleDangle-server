package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventRequestDto
import yapp.be.apiapplication.shelter.service.model.AddVolunteerEventResponseDto
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.port.inbound.AddVolunteerEventUseCase
import yapp.be.domain.port.inbound.GetShelterUserUseCase
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus

@Service
class VolunteerEventManageApplicationService(
    private val getShelterUserUseCase: GetShelterUserUseCase,
    private val addVolunteerEventUseCase: AddVolunteerEventUseCase
) {

    @Transactional
    fun addVolunteerEvent(shelterUserId: Long, reqDto: AddVolunteerEventRequestDto): AddVolunteerEventResponseDto {
        val shelterUser = getShelterUserUseCase.getShelterUserById(shelterUserId)

        val volunteerEvent = VolunteerEvent(
            title = reqDto.title,
            shelterId = shelterUser.shelterId,
            description = reqDto.description,
            recruitNum = reqDto.recruitNum,
            ageLimit = reqDto.ageLimit,
            volunteerEventCategory = reqDto.category,
            volunteerEventStatus = VolunteerEventStatus.IN_PROGRESS,
            startAt = reqDto.startAt,
            endAt = reqDto.endAt
        )
        val volunteerEventsId =
            reqDto.iteration?.let {
                addVolunteerEventUseCase.addVolunteerEventsWithIteration(
                    iteration = it,
                    volunteerEvent = volunteerEvent
                )
            } ?: addVolunteerEventUseCase.addVolunteerEvent(volunteerEvent)

        return AddVolunteerEventResponseDto(volunteerEventsId)
    }
}
