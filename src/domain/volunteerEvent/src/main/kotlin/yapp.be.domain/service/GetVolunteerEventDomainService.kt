package yapp.be.domain.service

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.model.VolunteerEvent
import yapp.be.domain.model.dto.DetailVolunteerEventDto
import yapp.be.domain.model.dto.ShelterSimpleVolunteerEventDto
import yapp.be.domain.model.dto.ShelterUserVolunteerEventStatDto
import yapp.be.domain.port.inbound.GetVolunteerEventUseCase
import yapp.be.domain.model.dto.VolunteerSimpleVolunteerEventDto
import yapp.be.domain.model.dto.VolunteerVolunteerEventStatDto
import yapp.be.domain.port.outbound.VolunteerEventQueryHandler
import yapp.be.model.enums.volunteerevent.UserEventParticipationStatus
import yapp.be.model.enums.volunteerevent.VolunteerEventStatus
import yapp.be.model.support.PagedResult

@Service
class GetVolunteerEventDomainService(
    private val volunteerEventQueryHandler: VolunteerEventQueryHandler
) : GetVolunteerEventUseCase {

    @Transactional(readOnly = true)
    override fun getVolunteerVolunteerEventStat(volunteerId: Long): VolunteerVolunteerEventStatDto {
        return volunteerEventQueryHandler.findVolunteerStatByVolunteerId(volunteerId)
    }

    @Transactional(readOnly = true)
    override fun getShelterVolunteerEventStat(shelterId: Long): ShelterUserVolunteerEventStatDto {
        return volunteerEventQueryHandler.findShelterUserStatByShelterId(shelterId)
    }

    @Transactional(readOnly = true)
    override fun getAllShelterVolunteerEventHistory(
        page: Int,
        shelterId: Long,
        status: VolunteerEventStatus?
    ): PagedResult<ShelterSimpleVolunteerEventDto> {

        return if (status == null) {
            volunteerEventQueryHandler.findAllShelterVolunteerEventByShelterId(
                page = page,
                shelterId = shelterId
            )
        } else {
            volunteerEventQueryHandler
                .findAllShelterVolunteerEventByShelterIdAndStatus(
                    page = page,
                    shelterId = shelterId,
                    status = status
                )
        }
    }

    @Transactional(readOnly = true)
    override fun getAllVolunteerVolunteerEventHistory(page: Int, volunteerId: Long, status: UserEventParticipationStatus?): PagedResult<VolunteerSimpleVolunteerEventDto> {
        return if (status == null) {
            volunteerEventQueryHandler
                .findAllVolunteerVolunteerEventByVolunteerId(
                    page = page,
                    volunteerId = volunteerId
                )
        } else {
            volunteerEventQueryHandler
                .findAllVolunteerVolunteerEventByVolunteerIdAndStatus(
                    page = page,
                    volunteerId = volunteerId,
                    status = status
                )
        }
    }

    @Transactional(readOnly = true)
    override fun getVolunteerEvent(shelterId: Long, volunteerEventId: Long): VolunteerEvent {
        return volunteerEventQueryHandler
            .findByIdAndShelterId(
                id = volunteerEventId,
                shelterId = shelterId
            )
    }

    @Transactional(readOnly = true)
    override fun getVolunteerEventDone(): List<VolunteerEvent> {
        return volunteerEventQueryHandler.findVolunteerEventDone()
    }

    @Transactional(readOnly = true)
    override fun getMemberDetailVolunteerEventInfo(shelterId: Long, volunteerId: Long, volunteerEventId: Long): DetailVolunteerEventDto {
        return volunteerEventQueryHandler.findDetailVolunteerEventInfoByIdAndShelterIdAndVolunteerId(
            id = volunteerEventId,
            volunteerId = volunteerId,
            shelterId = shelterId
        )
    }

    @Transactional(readOnly = true)
    override fun getNonMemberDetailVolunteerEventInfo(shelterId: Long, volunteerEventId: Long): DetailVolunteerEventDto {
        return volunteerEventQueryHandler
            .findDetailVolunteerEventInfoByIdAndShelterId(
                id = volunteerEventId,
                shelterId = shelterId
            )
    }

    @Transactional(readOnly = true)
    override fun getShelterUserDetailVolunteerEventInfo(shelterId: Long, volunteerEventId: Long): DetailVolunteerEventDto {
        return volunteerEventQueryHandler
            .findDetailVolunteerEventInfoByIdAndShelterId(
                id = volunteerEventId,
                shelterId = shelterId
            )
    }

    @Transactional(readOnly = true)
    override fun getShelterUserVolunteerEventsByDateRange(shelterId: Long, from: LocalDateTime, to: LocalDateTime): List<VolunteerSimpleVolunteerEventDto> {
        return volunteerEventQueryHandler
            .findAllVolunteerSimpleVolunteerEventInfosByShelterIdAndDateRange(
                shelterId = shelterId,
                from = from,
                to = to
            )
    }

    @Transactional(readOnly = true)
    override fun getMemberVolunteerEventsByDateRange(
        shelterId: Long,
        volunteerId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerEventDto> {
        return volunteerEventQueryHandler.findAllVolunteerSimpleVolunteerEventInfosWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
            shelterId = shelterId,
            volunteerId = volunteerId,
            from = from,
            to = to
        )
    }

    @Transactional(readOnly = true)
    override fun getNonMemberVolunteerEventsByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerEventDto> {
        return volunteerEventQueryHandler.findAllVolunteerSimpleVolunteerEventInfosByShelterIdAndDateRange(
            shelterId = shelterId,
            from = from,
            to = to
        )
    }
}
