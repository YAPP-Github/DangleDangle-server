package yapp.be.domain.model

data class VolunteerEventWaitingQueue (
    val userIdentifier: Identifier,
    val volunteerEventIdentifier: Identifier,
)
