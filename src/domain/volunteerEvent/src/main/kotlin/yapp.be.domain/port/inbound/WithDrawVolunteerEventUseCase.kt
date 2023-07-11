package yapp.be.domain.port.inbound

interface WithDrawVolunteerEventUseCase {
    fun withdrawVolunteerEvent(
        volunteerId: Long,
        volunteerEventId: Long,
        joinParticipants: List<Long>,
        waitingParticipants: List<Long>
    ): Result<Unit>
}
