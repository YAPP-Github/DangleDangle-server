package yapp.be.domain.volunteerActivity.port.inbound

interface WithDrawVolunteerActivityUseCase {

    fun withdrawJoinQueue(
        volunteerId: Long,
        volunteerEventId: Long
    )

    fun withdrawWaitingQueue(
        volunteerId: Long,
        volunteerEventId: Long
    )

    fun withdrawWaitingQueue(
        volunteerEventId: Long
    )
}
