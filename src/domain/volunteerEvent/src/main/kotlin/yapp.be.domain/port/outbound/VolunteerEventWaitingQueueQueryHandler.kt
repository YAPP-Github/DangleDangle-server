package yapp.be.domain.port.outbound

interface VolunteerWaitingQueueQueryHandler {
    fun countAll(): Int
}
