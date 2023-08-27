package yapp.be.apiapplication.shelter.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.shelter.service.model.DeleteShelterUserResponseDto
import yapp.be.apiapplication.shelter.service.model.GetShelterMyProfileResponseDto
import yapp.be.apiapplication.shelter.service.model.GetShelterMyVolunteerEventHistoryResponseDto
import yapp.be.apiapplication.shelter.service.model.ShelterVolunteerEventHistoryStatInfo
import yapp.be.domain.port.inbound.DeleteVolunteerEventUseCase
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.shelter.DeleteShelterUserUseCase
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.support.PagedResult

@Service
class ShelterMyApplicationService(
    private val getShelterUseCase: GetShelterUseCase,
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase,
    private val deleteShelterUserUseCase: DeleteShelterUserUseCase,
    private val deleteVolunteerEventUseCase: DeleteVolunteerEventUseCase,
) {
    @Transactional(readOnly = true)
    fun getShelterMyProfile(shelterId: Long): GetShelterMyProfileResponseDto {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        val volunteerEventHistoryStat = getVolunteerEventUseCase.getShelterVolunteerEventStat(shelterId)

        return GetShelterMyProfileResponseDto(
            name = shelter.name,
            historyStat = ShelterVolunteerEventHistoryStatInfo(
                done = volunteerEventHistoryStat.done,
                inProgress = volunteerEventHistoryStat.inProgress
            ),
            alarmEnabled = shelter.alarmEnabled
        )
    }

    @Transactional(readOnly = true)
    fun getShelterVolunteerEventHistories(
        page: Int,
        shelterId: Long,
        status: VolunteerEventStatus?
    ): PagedResult<GetShelterMyVolunteerEventHistoryResponseDto> {
        val shelter = getShelterUseCase.getShelterById(shelterId)
        val histories = getVolunteerEventUseCase
            .getAllShelterVolunteerEventHistory(
                page = page,
                status = status,
                shelterId = shelter.id
            )

        return PagedResult(
            pageNumber = histories.pageNumber,
            pageSize = histories.pageSize,
            content = histories.content.map {
                GetShelterMyVolunteerEventHistoryResponseDto(
                    volunteerEventId = it.volunteerEventId,
                    title = it.title,
                    category = it.category,
                    eventStatus = it.eventStatus,
                    startAt = it.startAt,
                    endAt = it.endAt,
                    recruitNum = it.recruitNum,
                    participantNum = it.participantNum,
                    waitingNum = it.waitingNum
                )
            }
        )
    }

    @Transactional
    fun withdrawShelterUser(
        shelterUserId: Long,
    ): DeleteShelterUserResponseDto {
        val deletedShelterId = deleteShelterUserUseCase.deleteShelterUser(shelterUserId)
        deleteVolunteerEventUseCase.deleteByShelterId(deletedShelterId)
        return DeleteShelterUserResponseDto(
            shelterId = deletedShelterId
        )
    }
}
