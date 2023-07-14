package yapp.be.domain.port.inbound

interface WithDrawVolunteerEventUseCase {

    fun withdrawJoinQueue(
        volunteerId: Long,
        volunteerEventId: Long
    )

    fun withdrawWaitingQueue(
        volunteerId: Long,
        volunteerEventId: Long
    )
}
