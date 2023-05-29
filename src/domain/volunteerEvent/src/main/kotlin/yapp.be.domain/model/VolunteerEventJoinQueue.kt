package yapp.be.domain.model

data class VolunteerEventJoinQueue (
    val userIdentifier: Identifier,
    val volunteerEventIdentifier: Identifier,
)
