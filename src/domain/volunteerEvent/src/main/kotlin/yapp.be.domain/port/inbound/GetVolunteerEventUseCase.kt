package yapp.be.domain.port.inbound

import yapp.be.domain.model.dto.SimpleVolunteerEventInfo

interface GetVolunteerEventUseCase {

    fun getMemberVolunteerEvents(
        shelterId: Long,
        volunteerId: Long
    ): List<SimpleVolunteerEventInfo>
    fun getNonMemberVolunteerEvents(
        shelterId: Long
    ): List<SimpleVolunteerEventInfo>
}
