package yapp.be.domain.port.inbound

import yapp.be.domain.model.dto.SimpleVolunteerEventInfo
import java.time.Month
import java.time.Year

interface GetVolunteerEventUseCase {

    fun getMemberVolunteerEventsByYearAndMonth(
        shelterId: Long,
        volunteerId: Long,
        year: Year,
        month: Month
    ): List<SimpleVolunteerEventInfo>
    fun getNonMemberVolunteerEventsByYearAndMonth(
        shelterId: Long,
        year: Year,
        month: Month
    ): List<SimpleVolunteerEventInfo>
}
