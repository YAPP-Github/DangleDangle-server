package yapp.be.apiapplication.volunteer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.volunteer.service.model.EditVolunteerMyProfileRequestDto
import yapp.be.apiapplication.volunteer.service.model.GetVolunteerBookMarkedShelterResponseDto
import yapp.be.apiapplication.volunteer.service.model.GetVolunteerMyProfileResponseDto
import yapp.be.apiapplication.volunteer.service.model.GetVolunteerVolunteerEventHistoryResponseDto
import yapp.be.apiapplication.volunteer.service.model.VolunteerVolunteerEventHistoryStatInfo
import yapp.be.apiapplication.volunteer.service.model.EditVolunteerMyProfileResponseDto
import yapp.be.apiapplication.volunteer.service.model.DeleteVolunteerResponseDto
import yapp.be.apiapplication.volunteer.service.model.BookMarkedShelterInfo
import yapp.be.apiapplication.volunteer.service.model.GetVolunteerUpcomingVolunteerEventResponseDto
import yapp.be.domain.port.inbound.DeleteVolunteerUseCase
import yapp.be.domain.port.inbound.EditVolunteerUseCase
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.GetVolunteerUseCase
import yapp.be.domain.port.inbound.model.EditVolunteerCommand
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.support.PagedResult

@Service
class VolunteerMyApplicationService(
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val getShelterUseCase: GetShelterUseCase,
    private val editVolunteerUseCase: EditVolunteerUseCase,
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase,
    private val deleteVolunteerUseCase: DeleteVolunteerUseCase,
) {

    @Transactional(readOnly = true)
    fun getVolunteerUpComingVolunteerEvent(volunteerId: Long): GetVolunteerUpcomingVolunteerEventResponseDto? {
        val volunteer = getVolunteerUseCase.getById(volunteerId)
        val upcomingVolunteerEvent = getVolunteerEventUseCase.getVolunteerUpComingVolunteerEvent(
            volunteerId = volunteer.id
        )

        return upcomingVolunteerEvent?.let {
            GetVolunteerUpcomingVolunteerEventResponseDto(
                shelterId = it.shelterId,
                shelterName = it.shelterName,
                shelterImageProfileUrl = it.shelterProfileImageUrl,
                volunteerEventId = it.volunteerEventId,
                title = it.title,
                startAt = it.startAt,
                endAt = it.endAt,
                recruitNum = it.recruitNum,
                participantNum = it.participantNum,
                waitingNum = it.waitingNum
            )
        }
    }

    @Transactional(readOnly = true)
    fun getVolunteerMyProfile(volunteerId: Long): GetVolunteerMyProfileResponseDto {
        val volunteer = getVolunteerUseCase.getById(volunteerId)
        val volunteerEventHistoryStat = getVolunteerEventUseCase
            .getVolunteerVolunteerEventStat(volunteerId)

        return GetVolunteerMyProfileResponseDto(
            nickName = volunteer.nickname,
            historyStat = VolunteerVolunteerEventHistoryStatInfo(
                done = volunteerEventHistoryStat.done,
                waiting = volunteerEventHistoryStat.waiting,
                joining = volunteerEventHistoryStat.joining
            ),
            alarmEnabled = volunteer.alarmEnabled,
            phoneNumber = volunteer.phone
        )
    }

    @Transactional(readOnly = true)
    fun getVolunteerVolunteerEventHistories(
        page: Int,
        volunteerId: Long,
        status: UserEventParticipationStatus?
    ): PagedResult<GetVolunteerVolunteerEventHistoryResponseDto> {
        val volunteer = getVolunteerUseCase.getById(volunteerId = volunteerId)
        val histories = getVolunteerEventUseCase
            .getAllVolunteerVolunteerEventHistory(
                page = page,
                volunteerId = volunteer.id,
                status = status
            )

        return PagedResult(
            pageNumber = histories.pageNumber,
            pageSize = histories.pageSize,
            content = histories.content.map {
                GetVolunteerVolunteerEventHistoryResponseDto(
                    shelterId = it.shelterId,
                    shelterName = it.shelterName,
                    shelterImageProfileUrl = it.shelterProfileImageUrl,
                    volunteerEventId = it.volunteerEventId,
                    title = it.title,
                    category = it.category,
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

    @Transactional(readOnly = true)
    fun getVolunteerBookMarkedShelters(
        volunteerId: Long
    ): GetVolunteerBookMarkedShelterResponseDto {
        val volunteer = getVolunteerUseCase.getById(volunteerId = volunteerId)
        val bookMarkedShelter = getShelterUseCase
            .getVolunteerBookMarkedShelterByVolunteerId(volunteer.id)

        return GetVolunteerBookMarkedShelterResponseDto(
            bookMarkedShelter.map {
                BookMarkedShelterInfo(
                    shelterId = it.id,
                    shelterName = it.name,
                    shelterProfileImageUrl = it.profileImageUrl
                )
            }
        )
    }

    @Transactional
    fun editMyProfile(
        volunteerId: Long,
        reqDto: EditVolunteerMyProfileRequestDto
    ): EditVolunteerMyProfileResponseDto {
        val command = EditVolunteerCommand(
            volunteerId = volunteerId,
            nickName = reqDto.nickName,
            phoneNum = reqDto.phoneNumber,
            alarmEnabled = reqDto.alarmEnabled
        )
        val updatedVolunteer = editVolunteerUseCase.updateVolunteer(command)

        return EditVolunteerMyProfileResponseDto(
            updatedVolunteer.id
        )
    }

    @Transactional
    fun withdrawVolunteer(
        volunteerId: Long,
    ): DeleteVolunteerResponseDto {
        val deletedVolunteer = deleteVolunteerUseCase.deleteVolunteer(volunteerId)

        return DeleteVolunteerResponseDto(
            deletedVolunteer.id
        )
    }
}
