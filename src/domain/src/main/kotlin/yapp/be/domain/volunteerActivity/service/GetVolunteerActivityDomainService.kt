package yapp.be.domain.volunteerActivity.service

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yapp.be.domain.volunteerActivity.model.VolunteerActivity
import yapp.be.domain.volunteerActivity.model.dto.*
import yapp.be.domain.volunteerActivity.port.inbound.GetVolunteerActivityUseCase
import yapp.be.domain.volunteerActivity.port.outbound.VolunteerActivityQueryHandler
import yapp.be.model.enums.volunteerActivity.UserEventParticipationStatus
import yapp.be.model.enums.volunteerActivity.VolunteerActivityCategory
import yapp.be.model.enums.volunteerActivity.VolunteerActivityStatus
import yapp.be.model.support.PagedResult

@Service
class GetVolunteerActivityDomainService(
    private val volunteerActivityQueryHandler: VolunteerActivityQueryHandler
) : GetVolunteerActivityUseCase {
    @Transactional(readOnly = true)
    override fun getVolunteerUpComingVolunteerEvent(volunteerId: Long): VolunteerSimpleVolunteerActivityDto? {
        return volunteerActivityQueryHandler.findUpcomingVolunteerActivityByVolunteerId(volunteerId)
    }

    @Transactional(readOnly = true)
    override fun getVolunteerVolunteerEventStat(volunteerId: Long): VolunteerVolunteerActivityStatDto {
        return volunteerActivityQueryHandler.findVolunteerStatByVolunteerId(volunteerId)
    }

    @Transactional(readOnly = true)
    override fun getShelterVolunteerEventStat(shelterId: Long): ShelterUserVolunteerActivityStatDto {
        return volunteerActivityQueryHandler.findShelterUserStatByShelterId(shelterId)
    }

    @Transactional(readOnly = true)
    override fun getAllShelterVolunteerEventHistory(
        page: Int,
        shelterId: Long,
        status: VolunteerActivityStatus?
    ): PagedResult<ShelterSimpleVolunteerActivityDto> {

        return if (status == null) {
            volunteerActivityQueryHandler.findAllShelterVolunteerActivityByShelterId(
                page = page,
                shelterId = shelterId
            )
        } else {
            volunteerActivityQueryHandler
                .findAllShelterVolunteerActivityByShelterIdAndStatus(
                    page = page,
                    shelterId = shelterId,
                    status = status
                )
        }
    }

    @Transactional(readOnly = true)
    override fun getAllVolunteerVolunteerEventHistory(page: Int, volunteerId: Long, status: UserEventParticipationStatus?): PagedResult<VolunteerSimpleVolunteerActivityDto> {
        return if (status == null) {
            volunteerActivityQueryHandler
                .findAllVolunteerVolunteerActivityByVolunteerId(
                    page = page,
                    volunteerId = volunteerId
                )
        } else {
            volunteerActivityQueryHandler
                .findAllVolunteerVolunteerActivityByVolunteerIdAndStatus(
                    page = page,
                    volunteerId = volunteerId,
                    status = status
                )
        }
    }

    @Transactional(readOnly = true)
    override fun getVolunteerEvent(shelterId: Long, volunteerEventId: Long): VolunteerActivity {
        return volunteerActivityQueryHandler
            .findByIdAndShelterId(
                id = volunteerEventId,
                shelterId = shelterId
            )
    }

    @Transactional(readOnly = true)
    override fun getVolunteerEventDone(): List<VolunteerActivity> {
        return volunteerActivityQueryHandler.findVolunteerActivityDone()
    }

    @Transactional(readOnly = true)
    override fun getMemberDetailVolunteerEventInfo(shelterId: Long, volunteerId: Long, volunteerEventId: Long): DetailVolunteerActivityDto {
        return volunteerActivityQueryHandler.findDetailVolunteerActivityInfoByIdAndShelterIdAndVolunteerId(
            id = volunteerEventId,
            volunteerId = volunteerId,
            shelterId = shelterId
        )
    }

    @Transactional(readOnly = true)
    override fun getNonMemberDetailVolunteerEventInfo(shelterId: Long, volunteerEventId: Long): DetailVolunteerActivityDto {
        return volunteerActivityQueryHandler
            .findDetailVolunteerActivityInfoByIdAndShelterId(
                id = volunteerEventId,
                shelterId = shelterId
            )
    }

    @Transactional(readOnly = true)
    override fun getShelterUserDetailVolunteerEventInfo(shelterId: Long, volunteerEventId: Long): DetailVolunteerActivityDto {
        return volunteerActivityQueryHandler
            .findDetailVolunteerActivityInfoByIdAndShelterId(
                id = volunteerEventId,
                shelterId = shelterId
            )
    }

    @Transactional(readOnly = true)
    override fun getShelterUserVolunteerEventsByDateRange(shelterId: Long, from: LocalDateTime, to: LocalDateTime): List<VolunteerSimpleVolunteerActivityDto> {
        return volunteerActivityQueryHandler
            .findAllVolunteerSimpleVolunteerActivityInfosByShelterIdAndDateRange(
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
    ): List<VolunteerSimpleVolunteerActivityDto> {
        return volunteerActivityQueryHandler.findAllVolunteerSimpleVolunteerActivityInfosWithMyParticipationStatusByShelterIdAndVolunteerIdAndDateRange(
            shelterId = shelterId,
            volunteerId = volunteerId,
            from = from,
            to = to
        )
    }

    @Transactional(readOnly = true)
    override fun getVolunteerEventsByDateRangeAndCategory(shelterId: Long, from: LocalDateTime, to: LocalDateTime, category: VolunteerActivityCategory?): List<VolunteerSimpleVolunteerActivityDto> {
        return volunteerActivityQueryHandler.findAllVolunteerSimpleVolunteerActivityInfosWithMyParticipationStatusByShelterIdAndDateRangeAndCategory(
            shelterId = shelterId,
            from = from,
            to = to,
            category = category,
        )
    }

    @Transactional(readOnly = true)
    override fun getVolunteerEventsByDateRangeAndCategoryAndStatus(shelterId: Long, from: LocalDateTime, to: LocalDateTime, category: List<VolunteerActivityCategory>?, status: VolunteerActivityStatus?): List<VolunteerSimpleVolunteerActivityDto> {
        return volunteerActivityQueryHandler.findAllVolunteerSimpleVolunteerActivityInfosWithMyParticipationStatusByDateRangeAndCategoryAndStatus(
            shelterId = shelterId,
            from = from,
            to = to,
            category = category,
            status = status,
        )
    }

    @Transactional(readOnly = true)
    override fun getNonMemberVolunteerEventsByDateRange(
        shelterId: Long,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<VolunteerSimpleVolunteerActivityDto> {
        return volunteerActivityQueryHandler.findAllVolunteerSimpleVolunteerActivityInfosByShelterIdAndDateRange(
            shelterId = shelterId,
            from = from,
            to = to
        )
    }
}
