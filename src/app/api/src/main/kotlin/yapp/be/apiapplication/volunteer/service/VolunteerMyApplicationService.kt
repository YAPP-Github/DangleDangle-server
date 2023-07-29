package yapp.be.apiapplication.volunteer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.apiapplication.volunteer.service.model.BookMarkedShelterInfo
import yapp.be.apiapplication.volunteer.service.model.EditVolunteerMyProfileRequestDto
import yapp.be.apiapplication.volunteer.service.model.EditVolunteerMyProfileResponseDto
import yapp.be.apiapplication.volunteer.service.model.GetVolunteerBookMarkedShelterResponseDto
import yapp.be.apiapplication.volunteer.service.model.GetVolunteerMyProfileResponseDto
import yapp.be.apiapplication.volunteer.service.model.VolunteerVolunteerEventHistoryStatInfo
import yapp.be.domain.port.inbound.EditVolunteerUseCase
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.port.inbound.GetVolunteerUseCase
import yapp.be.domain.port.inbound.model.EditVolunteerCommand
import yapp.be.domain.port.inbound.shelter.GetShelterUseCase

@Service
class VolunteerMyApplicationService(
    private val getVolunteerUseCase: GetVolunteerUseCase,
    private val getShelterUseCase: GetShelterUseCase,
    private val editVolunteerUseCase: EditVolunteerUseCase,
    private val getVolunteerEventUseCase: GetVolunteerEventUseCase
) {

    @Transactional(readOnly = true)
    fun getVolunteerMyProfile(volunteerId: Long): GetVolunteerMyProfileResponseDto {
        val volunteer = getVolunteerUseCase.getById(volunteerId = volunteerId)
        val volunteerEventHistoryStat = getVolunteerEventUseCase
            .getVolunteerVolunteerEventStat(volunteerId)

        return GetVolunteerMyProfileResponseDto(
            nickName = volunteer.nickname,
            historyStat = VolunteerVolunteerEventHistoryStatInfo(
                done = volunteerEventHistoryStat.done,
                waiting = volunteerEventHistoryStat.waiting,
                joining = volunteerEventHistoryStat.joining
            ),
            alarm = volunteer.alarmEnabled,
            phoneNumber = volunteer.phone
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
                    name = it.name
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
            alarmEnabled = reqDto.alarm
        )
        val updatedVolunteer = editVolunteerUseCase.updateVolunteer(command)

        return EditVolunteerMyProfileResponseDto(
            updatedVolunteer.id
        )
    }
}
