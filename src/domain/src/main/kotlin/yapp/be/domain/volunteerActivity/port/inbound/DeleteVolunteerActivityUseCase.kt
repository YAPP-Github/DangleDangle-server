package yapp.be.domain.volunteerActivity.port.inbound

interface DeleteVolunteerActivityUseCase {
    fun deleteByIdAndShelterId(
        id: Long,
        shelterId: Long
    )
    fun deleteByShelterId(shelterId: Long)

    fun deleteAllVolunteerRelatedVolunteerEvents(
        volunteerId: Long
    )

    fun hardDeleteByVolunteerId(volunteerId: Long)
}
